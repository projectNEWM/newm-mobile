import Foundation
import SwiftUI
import shared

//public struct NFTTrack {
//	public let title: String
//	public let artistName: String
//	public let url: URL
//	public let image: URL
//	
//	public init(title: String, artistName: String, url: URL, image: URL) {
//		self.title = title
//		self.artistName = artistName
//		self.url = url
//		self.image = image
//	}
//}
//
//public struct AudioTrack {
//	public let title: String
//	public let artistName: String
//	public let url: URL
//	public let image: URL
//	
//	public init(title: String, artistName: String, url: URL, image: URL) {
//		self.title = title
//		self.artistName = artistName
//		self.url = url
//		self.image = image
//	}
//}

//public protocol AudioPlayer: ObservableObject {
//	var playbackInfo: PlaybackInfo { get }
//	var track: NFTTrack? { get set }
//
//	func prev()
//	func next()
//	func cycleRepeatMode()
//	func seek(to time: Float)
//	func play()
//	func pause()
//}

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
	public var currentTime: Float
	public var totalTime: Float
	
	public init(isPlaying: Bool = false, repeatMode: RepeatMode? = nil, shuffle: Bool = false, currentTime: Float = 0, totalTime: Float = 30) {
		self.isPlaying = isPlaying
		self.repeatMode = repeatMode
		self.shuffle = shuffle
		self.currentTime = currentTime
		self.totalTime = totalTime > 0 ? totalTime : 30
	}
	
	public var percentPlayed: Float {
		guard totalTime > 0 else { return 0 }
		return currentTime / totalTime
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
