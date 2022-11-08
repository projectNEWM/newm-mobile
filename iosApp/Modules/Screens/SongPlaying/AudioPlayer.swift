import Foundation
import Combine

enum PlaybackState {
	case playing
	case paused
	case stopped
}

enum RepeatMode {
	case one
	case all
}

protocol AudioPlayer: ObservableObject {
	func setSongId(_ songId: String)
	
	//TODO: change this to AsyncStream
	var currentTime: Int { get }
	var totalTime: Int { get }
	var playbackState: PlaybackState { get set }
	var repeatMode: RepeatMode? { get set }
	var shuffle: Bool { get set }
}
