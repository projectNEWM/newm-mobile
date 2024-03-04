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
	public enum RepeatMode {
		case all
		case one
	}
	public enum PlaybackState {
		case playing
		case paused
		case stopped
		case buffering
	}
	static let sharedPlayer = VLCAudioPlayer()
	
	private var mediaPlayer: VLCMediaPlayer
	private var playQueue: PlayQueue = PlayQueue()
	@Published private var fileManager = FileManagerService()
	lazy private var delegate: VLCAudioPlayerDelegate = VLCAudioPlayerDelegate(updateData: { [weak self] in self?.updateData($0) })
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
	}
	
	public func play() {
		mediaPlayer.play()
	}
	
	private func handleWalletDisconnect() {
		stop()
		playQueue.originalTracks = []
		removeDownloadedSongs()
	}
	
	private func playCurrentIndexInQueue() {
		guard let currentTrack else {
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
//		guard let currentQueueIndex else { return }
//		if currentQueueIndex < activePlayQueue.count-1 {
//			self.currentQueueIndex = currentQueueIndex + 1
//		} else {
//			if repeatMode == .all {
//				self.currentQueueIndex = 0
//			}
//		}
//		playCurrentIndexInQueue()
	}
	
	public func prev() {
//		guard let currentQueueIndex else { return }
//		if let currentTime, currentTime < 3 {
//			if currentQueueIndex > 0 {
//				self.currentQueueIndex = currentQueueIndex - 1
//			} else if repeatMode == .all {
//				self.currentQueueIndex = activePlayQueue.count-1
//			}
//		}
//		playCurrentIndexInQueue()
	}
	
	public func seek(toTrack track: NFTTrack) {
		guard track != currentTrack else {
			mediaPlayer.time = VLCTime(int: 0)
			return
		}
//		currentQueueIndex = activePlayQueue.firstIndex(of: track)
		playCurrentIndexInQueue()
	}
	
	public func seek(toTime time: Double) {
		mediaPlayer.time = VLCTime(int: time.secondsToMilliseconds)
	}
	
	public var playQueueIsEmpty: Bool {
		playQueue.isEmpty
	}
	
	public func setTracks(_ tracks: Set<NFTTrack>, playFirstTrack: Bool = true) {
		playQueue.originalTracks = tracks
		if playFirstTrack {
			try! playQueue.seekToFirst()
			playCurrentIndexInQueue()
		}
	}
	
	public var isPlaying: Bool {
		state == .playing
	}
	
	var currentTrack: NFTTrack? {
		try! playQueue.currentTrack()
	}
	
	public func trackIsPlaying(_ track: NFTTrack) -> Bool {
		currentTrack == track
	}
	
	public func trackIsDownloaded(_ track: NFTTrack) -> Bool {
		fileManager.fileExists(for: URL(string: track.audioUrl)!)
	}
	
	public func cycleRepeatMode() {
//		repeatMode = {
//			switch repeatMode {
//			case .all:
//				return .one
//			case .one:
//				return nil
//			case nil:
//				return .all
//			}
//		}()
	}
	
	public func removeDownloadedSongs() {
		fileManager.clearFiles()
	}
	
	public func removeDownloadedSong(_ song: NFTTrack) {
		fileManager.clearFile(at: URL(string: song.audioUrl)!)
	}
	
	fileprivate func updateData(_ aNotification: Foundation.Notification) {
		guard let player = aNotification.vlcPlayer else { return }
//		if player.state == .ended {
//			if repeatMode == .one {
//				prev()
//			} else {
//				next()
//			}
//		}
		objectWillChange.send()
		print("player state: \(VLCMediaPlayerStateToString(player.state))")
		print("media state: \(player.media?.state.description ?? "")")
		print("mediaplayer isplaying: \(isPlaying)")
	}
}

fileprivate class VLCAudioPlayerDelegate: NSObject, VLCMediaPlayerDelegate {
	let updateData: (Foundation.Notification) -> Void
	
	init(updateData: @escaping (Foundation.Notification) -> Void) {
		self.updateData = updateData
	}
	
	func mediaPlayerStateChanged(_ aNotification: Foundation.Notification) {
		updateData(aNotification)
	}
	
	func mediaPlayerTimeChanged(_ aNotification: Foundation.Notification) {
		updateData(aNotification)
	}
	
	func mediaPlayerTitleChanged(_ aNotification: Foundation.Notification) {
		updateData(aNotification)
	}
	
	func mediaPlayerChapterChanged(_ aNotification: Foundation.Notification) {
		updateData(aNotification)
	}
}

//extension VLCAudioPlayer {
//	var filteredSortedNFTTracks: [NFTTrack] {
//		var tracks = tracks
//		
//		if filterText.isEmpty == false {
//			tracks = tracks.filter {
//				$0.title.localizedCaseInsensitiveContains(filterText)
//			}
//		}
//		
//		if let comparator = sort.comparator {
//			tracks = tracks.sorted(by: comparator)
//		}
//		
//		tracks = tracks.filter { $0.duration < durationFilter }
//		
//		return tracks
//	}
//}
