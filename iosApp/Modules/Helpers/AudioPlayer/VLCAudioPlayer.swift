import Foundation
import AVFoundation.AVPlayer
import shared
import VLCKitSPM
import Resolver
import Files
import Combine

public class VLCAudioPlayer: ObservableObject {
	public enum RepeatMode {
		case all
		case one
	}
	static let shared = VLCAudioPlayer()
	
	private var mediaPlayer: VLCMediaPlayer
	private var playQueue: [NFTTrack] = [] {
		didSet {
			if shuffle {
				shuffledPlayQueue = playQueue.shuffled()
			}
			if playQueueIsEmpty {
				mediaPlayer.stop()
				mediaPlayer.media = nil
				currentQueueIndex = nil
			}
		}
	}
	private var shuffledPlayQueue: [NFTTrack] = []
	private var activePlayQueue: [NFTTrack] { shuffle ? shuffledPlayQueue : playQueue }
	private let fileManager = FileManagerService()
	lazy private var delegate: VLCAudioPlayerDelegate = VLCAudioPlayerDelegate(updateData: { [weak self] in self?.updateData($0) })
	private var downloadTask: Task<Void, Error>?
	
	@MainActor
	@Published public private(set) var loadingProgress: [NFTTrack: Double] = [:]
	
	public var state: VLCMediaPlayerState? { mediaPlayer.state }
	public var duration: TimeInterval? { mediaPlayer.media?.length.seconds }
	public var currentTime: TimeInterval? { mediaPlayer.time.seconds }
	public var percentPlayed: Float? { mediaPlayer.position }
	public var title: String? { mediaPlayer.media?.metaData.title }
	public var artist: String? { mediaPlayer.media?.metaData.artist }
	public var artworkUrl: URL? { mediaPlayer.media?.metaData.artworkURL }
	public var error: Bool { mediaPlayer.state == .error }
	@Published private var currentQueueIndex: Int?
	@Published public var shuffle: Bool = false {
		willSet {
			if newValue {
				shuffledPlayQueue = playQueue.shuffled()
				currentQueueIndex = currentTrack.flatMap(shuffledPlayQueue.firstIndex)
			} else {
				currentQueueIndex = currentTrack.flatMap(playQueue.firstIndex)
			}
		}
	}
	@Published public var repeatMode: RepeatMode?
	
	private init() {
		mediaPlayer = VLCMediaPlayer()
		mediaPlayer.delegate = delegate
	}
	
	public func play() {
		mediaPlayer.play()
	}
	
	@MainActor
	private func playCurrentIndexInQueue() async throws {
		do {
			guard let currentTrack else {
				return
			}
			let fileUrl = try await fileManager.getFile(forTrack: currentTrack) { [weak self] progress in
				guard let self else { return }
				print("progress for [\(currentTrack.title)]: \(progress)")
				DispatchQueue.main.async {
					self.loadingProgress[currentTrack] = progress
				}
			}
			
			loadingProgress.removeValue(forKey: currentTrack)
			
			mediaPlayer.media = currentTrack.vlcMedia(fileUrl: fileUrl)
			mediaPlayer.play()
		} catch {
			print(error)
		}
	}
	
	public func pause() {
		mediaPlayer.pause()
	}
	
	public func stop() {
		mediaPlayer.stop()
	}
	
	public func next() {
		guard let currentQueueIndex else { return }
		if currentQueueIndex < activePlayQueue.count-1 {
			self.currentQueueIndex = currentQueueIndex + 1
		} else {
			if repeatMode == .all {
				self.currentQueueIndex = 0
			}
		}
		Task {
			try! await playCurrentIndexInQueue()
		}
	}
	
	public func prev() {
		guard let currentQueueIndex else { return }
		if let currentTime, currentTime < 3 {
			if currentQueueIndex > 0 {
				self.currentQueueIndex = currentQueueIndex - 1
			} else if repeatMode == .all {
				self.currentQueueIndex = activePlayQueue.count-1
			}
		}
		Task {
			try! await playCurrentIndexInQueue()
		}
	}
	
	public func seek(toTrack track: NFTTrack) {
		currentQueueIndex = activePlayQueue.firstIndex(of: track)
		Task {
			try! await playCurrentIndexInQueue()
		}
	}
	
	public func seek(toTime time: Double) {
		mediaPlayer.time = VLCTime(int: time.secondsToMilliseconds)
	}
	
	public var playQueueIsEmpty: Bool {
		playQueue.isEmpty
	}
	
	public func setPlayQueue(_ tracks: [NFTTrack], playFirstTrack: Bool = true) {
		playQueue = tracks
	}
	
	public var isPlaying: Bool {
		mediaPlayer.isPlaying
	}
	
	private var currentTrack: NFTTrack? {
		guard let currentQueueIndex else { return nil }
		return activePlayQueue[currentQueueIndex]
	}
	
	public func trackIsPlaying(_ track: NFTTrack) -> Bool {
		currentTrack == track
	}
	
	public func trackIsDownloaded(_ track: NFTTrack) -> Bool {
		fileManager.fileExists(for: URL(string: track.audioUrl)!)
	}
	
	public func cycleRepeatMode() {
		repeatMode = {
			switch repeatMode {
			case .all:
				return .one
			case .one:
				return nil
			case nil:
				return .all
			}
		}()
	}
	
	public func removeDownloadedSongs() {
		fileManager.clearFiles()
	}
	
	fileprivate func updateData(_ aNotification: Foundation.Notification) {
		guard let player = aNotification.vlcPlayer else { return }
		if player.state == .ended {
			if repeatMode == .one {
				prev()
			} else {
				next()
			}
		}
		objectWillChange.send()
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

private extension NFTTrack {
	func vlcMedia(fileUrl: URL) -> VLCMedia {
		let media = VLCMedia(url: fileUrl)
		media.metaData.artist = artists.joined(separator: ", ")
		media.metaData.title = title
		media.metaData.artworkURL = URL(string: imageUrl)!
		return media
	}
}

private extension Foundation.Notification {
	var vlcPlayer: VLCMediaPlayer? {
		object as? VLCMediaPlayer
	}
}

private extension VLCTime {
	var seconds: Double? {
		value.flatMap { $0.doubleValue / 1_000 }
	}
}

extension Double {
	var secondsToMilliseconds: Int32 {
		Int32(self * 1000.0)
	}
}
