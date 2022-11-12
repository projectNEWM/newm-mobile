import Foundation
import SwiftUI

public protocol AudioPlayer: ObservableObject {
	var songInfo: SongInfo? { set get }
	var playbackInfo: PlaybackInfo { set get }

	func prev()
	func next()
	
	func cycleRepeatMode()
	func setSongId(_ songId: String)
}

public extension AudioPlayer {
	var percentPlayed: CGFloat {
		guard let songInfo, songInfo.totalTime > 0 else { return 0 }
		return CGFloat(songInfo.currentTime) / CGFloat(songInfo.totalTime)
	}
	
	func cycleRepeatMode() {
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
	
	public init(currentTime: Int, totalTime: Int, songID: String) {
		self.currentTime = currentTime
		self.totalTime = totalTime
		self.songID = songID
	}
}

public struct PlaybackInfo {
	public var isPlaying: Bool
	public var repeatMode: RepeatMode?
	public var shuffle: Bool
	
	public init(isPlaying: Bool = false, repeatMode: RepeatMode? = nil, shuffle: Bool = false) {
		self.isPlaying = isPlaying
		self.repeatMode = repeatMode
		self.shuffle = shuffle
	}
}
