import Foundation
import SwiftUI
import shared

public protocol AudioPlayer: ObservableObject {
	var song: Song? { set get }
	var playbackInfo: PlaybackInfo { set get }

	func prev()
	func next()
	
	func cycleRepeatMode()
}

public extension AudioPlayer {
	func cycleRepeatMode() {
		switch playbackInfo.repeatMode {
		case .none:
			playbackInfo.repeatMode = .one
		case .one:
			playbackInfo.repeatMode = .all
		case .all:
			playbackInfo.repeatMode = .none
		}
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

public struct PlaybackInfo {
	public var isPlaying: Bool
	public var repeatMode: RepeatMode?
	public var shuffle: Bool
	public var currentTime: Int
	public var totalTime: Int
	
	public init(isPlaying: Bool = false, repeatMode: RepeatMode? = nil, shuffle: Bool = false, currentTime: Int = 0, totalTime: Int = 30) {
		self.isPlaying = isPlaying
		self.repeatMode = repeatMode
		self.shuffle = shuffle
		self.currentTime = currentTime
		self.totalTime = totalTime
	}
	
	public var percentPlayed: CGFloat {
		guard totalTime > 0 else { return 0 }
		return CGFloat(currentTime) / CGFloat(totalTime)
	}
	
	mutating
	public func cycleRepeatMode() {
		switch repeatMode {
		case .none:
			repeatMode = .one
		case .one:
			repeatMode = .all
		case .all:
			repeatMode = .none
		}
	}
}
