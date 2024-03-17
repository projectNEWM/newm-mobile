import Foundation
import AVFoundation.AVPlayer
import shared
import VLCKitSPM
import Resolver
import Files
import Combine
import ModuleLinker
import Utilities
import OrderedCollections

public class VLCAudioPlayer: ObservableObject {
	public enum PlaybackState {
		case playing
		case paused
		case stopped
		case buffering
	}
	static let sharedPlayer = VLCAudioPlayer()
	
	private var mediaPlayer: VLCMediaPlayer
	@Published private var playQueue: PlayQueue = PlayQueue()
	@Published private var fileManager = FileManagerService()
	lazy private var delegate: VLCAudioPlayerDelegate = VLCAudioPlayerDelegate()
	private var cancels = Set<AnyCancellable>()
	
	@MainActor
	@Published public private(set) var loadingProgress: [NFTTrack: Double] = [:]
	
	public var state: PlaybackState {
		if mediaPlayer.isPlaying {
			return .playing
		} else if mediaPlayer.state == .buffering {
			return .buffering
		} else if mediaPlayer.state == .paused {
			return .paused
		} else {
			return .stopped
		}
	}
	public var duration: TimeInterval? { mediaPlayer.media?.length.seconds }
	public var currentTime: TimeInterval? { mediaPlayer.time.seconds }
	public var percentPlayed: Float? { mediaPlayer.position }
	public var title: String? { mediaPlayer.media?.metaData.title }
	public var artist: String? { mediaPlayer.media?.metaData.artist }
	public var artworkUrl: URL? { mediaPlayer.media?.metaData.artworkURL }
	public var willPlay: Bool { mediaPlayer.willPlay }
	@Injected var errorReporter: any ErrorReporting
	public var errors = ErrorSet()
		
	public var textFilter: String? {
		get { playQueue.textFilter }
		set { playQueue.textFilter = newValue }
	}
	
	public var durationFilter: Int? {
		get { playQueue.durationFilter }
		set { playQueue.durationFilter = newValue }
	}

	public var shuffle: Bool {
		get { playQueue.shuffle }
		set { playQueue.shuffle = newValue }
	}
	
	public var repeatMode: RepeatMode {
		get { playQueue.repeatMode }
		set { playQueue.repeatMode = newValue }
	}
	
	public var sort: AudioPlayerSort {
		get { playQueue.sortCriteria }
		set { playQueue.sortCriteria = newValue }
	}
	
	private init() {
		mediaPlayer = VLCMediaPlayer()
		mediaPlayer.delegate = delegate
		fileManager.objectWillChange
			.receive(on: DispatchQueue.main)
			.sink { [weak self] in
				self?.objectWillChange.send()
			}.store(in: &cancels)
		
		NotificationCenter.default.publisher(for: Notification.Name(Notification().walletConnectionStateChanged)).sink { [weak self] _ in
			self?.handleWalletDisconnect()
		}.store(in: &cancels)
		
		setUpDelegateHandling()
	}
	
	private func setUpDelegateHandling() {
		Task {
			for await _ in delegate.stream {
				if mediaPlayer.state == .ended, let _ = playQueue.nextTrack() {
					playCurrentTrackInQueue()
				} else if mediaPlayer.state == .error {
					//TODO: this isn't getting hit when a song fails to load
					errors.append(NEWMError(errorDescription: "Unable to load \(currentTrack?.title ?? "song")", failureReason: nil, recoverySuggestion: nil, underlyingError: nil))
				}
				DispatchQueue.main.async { [weak self] in
					self?.objectWillChange.send()
				}
				print("player state: \(VLCMediaPlayerStateToString(mediaPlayer.state))")
				print("media state: \(mediaPlayer.media?.state.description ?? "")")
				print("mediaplayer isplaying: \(isPlaying)")
			}
		}
	}
	
	public func setTracks(_ tracks: Set<NFTTrack>) {
		playQueue.originalTracks = tracks
	}
	
	public var hasNextTrack: Bool {
		playQueue.hasNextTrack
	}
	
	public var hasPrevTrack: Bool {
		playQueue.hasPrevTrack
	}
	
	public func play() {
		mediaPlayer.play()
	}
		
	private func handleWalletDisconnect() {
		stop()
		playQueue.originalTracks = []
		removeDownloadedSongs()
	}
	
	private func playCurrentTrackInQueue() {
		guard let currentTrack else {
			stop()
			mediaPlayer.media = nil
			return
		}
		
		mediaPlayer.media = currentTrack.vlcMedia(fileUrl: fileManager.getPlaybackURL(for: URL(string: currentTrack.audioUrl)!))
		mediaPlayer.play()
	}
	
	@MainActor
	public func downloadTrack(_ track: NFTTrack) async throws {
		guard trackIsDownloaded(track) == false, loadingProgress[track] == nil else {
			return
		}
		
		defer {
			loadingProgress.removeValue(forKey: track)
		}
		
		try await fileManager.download(track: track) { [weak self] progress in
			guard let self else { return }
			print("progress for [\(track.title)]: \(progress)")
			DispatchQueue.main.async {
				self.loadingProgress[track] = progress
			}
		}
	}
	
	@MainActor
	public func cancelDownload(_ track: NFTTrack) {
		fileManager.cancelDownload(track: track)
	}
	
	public func pause() {
		mediaPlayer.pause()
	}
	
	public func stop() {
		mediaPlayer.stop()
	}
	
	public func next() {
		playQueue.nextTrack(userInitiated: true)
		playCurrentTrackInQueue()
	}
	
	public func prev() {
		playQueue.previousTrack()
		playCurrentTrackInQueue()
	}
	
	public func seek(toTrack track: NFTTrack) {
		guard track != currentTrack else {
			mediaPlayer.time = VLCTime(int: 0)
			return
		}
		try? playQueue.seekToTrack(track)
		playCurrentTrackInQueue()
	}
	
	public func seek(toTime time: Double) {
		mediaPlayer.time = VLCTime(int: time.secondsToMilliseconds)
	}
	
	public var playQueueIsEmpty: Bool {
		playQueue.isEmpty
	}
	
	public func setTracks(_ tracks: Set<NFTTrack>, playFirstTrack: Bool = true) {
		playQueue.originalTracks = tracks
		try! playQueue.seekToFirst()
		if playFirstTrack {
			playCurrentTrackInQueue()
		}
	}
	
	public var isPlaying: Bool {
		state == .playing
	}
	
	public var currentTrack: NFTTrack? {
		try? playQueue.currentTrack()
	}
	
	public func trackIsPlaying(_ track: NFTTrack) -> Bool {
		currentTrack == track
	}
	
	public func trackIsDownloaded(_ track: NFTTrack) -> Bool {
		fileManager.fileExists(for: URL(string: track.audioUrl)!)
	}
	
	public func cycleRepeatMode() {
		playQueue.cycleRepeatMode()
	}
	
	public func removeDownloadedSongs() {
		fileManager.clearFiles()
	}
	
	public func removeDownloadedSong(_ song: NFTTrack) {
		fileManager.clearFile(at: URL(string: song.audioUrl)!)
	}
	
	public func cycleTitleSort() {
		playQueue.cycleTitleSort()
	}
	
	public func cycleArtistSort() {
		playQueue.cycleArtistSort()
	}
	
	public func cycleLengthSort() {
		playQueue.cycleDurationSort()
	}
}

fileprivate class VLCAudioPlayerDelegate: NSObject, VLCMediaPlayerDelegate {
	var stream: AsyncStream<Void> {
		AsyncStream { [weak self] continuation in
			self?.continuation = continuation
		}
	}

	private var continuation: AsyncStream<Void>.Continuation!
	
	func mediaPlayerStateChanged(_ aNotification: Foundation.Notification) {
		continuation.yield()
	}
	
	func mediaPlayerTimeChanged(_ aNotification: Foundation.Notification) {
		continuation.yield()
	}
	
	func mediaPlayerTitleChanged(_ aNotification: Foundation.Notification) {
		continuation.yield()
	}
	
	func mediaPlayerChapterChanged(_ aNotification: Foundation.Notification) {
		continuation.yield()
	}
	
	deinit {
		continuation.finish()
	}
}
