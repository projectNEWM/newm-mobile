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

public protocol AudioPlayer: ObservableObject {
	func setSongId(_ songId: String)
	
	//TODO: change this to AsyncStream
	var currentTime: Int { get }
	var totalTime: Int { get }
	var isPlaying: Bool { set get }
	var repeatMode: RepeatMode? { get }
	var shuffle: Bool { get }
	
	func prev()
	func next()
	
	func cycleRepeatMode()
}

public extension AudioPlayer {
	var percentPlayed: CGFloat { CGFloat(currentTime) / CGFloat(max(totalTime, 1)) }
}
