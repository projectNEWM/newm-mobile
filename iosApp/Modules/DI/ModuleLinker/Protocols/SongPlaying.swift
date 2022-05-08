import Foundation
import SwiftUI
import Combine

public protocol SongPlayingViewProviding {
	func songPlayingView(id: String) -> AnyView
}

public protocol SongInfoUseCaseProtocol {
	func execute() -> SongInfo
	init(id: String)
}

public struct SongInfo {
	public typealias Seconds = Int
	public let songTitle: String
	public let artistName: String
	public let shareCount: String
	public let starCount: String
	public let songLength: Seconds
	public let lyrics: String
	public let backgroundImage: URL
	public let albumImage: URL
	
	public init(songTitle: String, artistName: String, shareCount: String, starCount: String, songLength: SongInfo.Seconds, lyrics: String, backgroundImage: URL, albumImage: URL) {
		self.songTitle = songTitle
		self.artistName = artistName
		self.shareCount = shareCount
		self.starCount = starCount
		self.songLength = songLength
		self.lyrics = lyrics
		self.backgroundImage = backgroundImage
		self.albumImage = albumImage
	}
}

public enum PlaybackState {
	case playing
	case paused
}

public protocol MusicPlayerUseCaseProtocol {
	var playbackTime: AnyPublisher<Int, Never> { get }
	var playbackState: AnyPublisher<PlaybackState, Never> { get }
	
	func play()
	func pause()
	func stop()
	
	init(id: String)
}
