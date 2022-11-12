import Foundation
import Combine

public enum RepeatMode {
	case one
	case all
}

public extension Optional<RepeatMode> {
	mutating
	func cycle() {
		switch self {
		case .one:
			self = .all
		case .all:
			self = .none
		case .none:
			self = .one
		}
	}
}

public struct SongInfo {
	public let currentTime: Int
	public let totalTime: Int
	public let songID: String
}

public struct PlaybackInfo {
	public var isPlaying: Bool = false
	public var repeatMode: RepeatMode? = nil
	public var shuffle: Bool = false
}

public protocol AudioPlayer: ObservableObject {
	var songInfo: SongInfo? { set get }
	var playbackInfo: PlaybackInfo? { set get }

	func prev()
	func next()
	
	func cycleRepeatMode()
}

public extension AudioPlayer {
	var percentPlayed: CGFloat {
		guard let songInfo, songInfo.totalTime > 0 else { return 0 }
		return CGFloat(songInfo.currentTime) / CGFloat(songInfo.totalTime)
	}
	
	func cycleRepeatMode() {
		guard var playbackInfo else { fatalError("audio player not set up") }
		switch playbackInfo.repeatMode {
		case .none:
			playbackInfo.repeatMode = .one
		case .one:
			playbackInfo.repeatMode = .all
		case .all:
			playbackInfo.repeatMode = .none
		}
		self.playbackInfo = playbackInfo
	}
}
