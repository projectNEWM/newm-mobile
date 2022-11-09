import Foundation
import SwiftUI
import shared

public protocol SongPlayingViewProviding {
	func songPlayingView(id: String) -> AnyView
}

public struct SongInfo {
	public typealias Seconds = Int
	public let songTitle: String
	public let artist: Artist
	public let shareCount: String
	public let starCount: String
	public let songLength: Seconds
	public let lyrics: String
	public let backgroundImage: URL
	public let albumImage: URL
	public let id: String
	public var favorited: Bool {
		didSet {
			//TODO: network call/update cache
		}
	}
	
	public init(songTitle: String, artist: Artist, shareCount: String, starCount: String, songLength: SongInfo.Seconds, lyrics: String, backgroundImage: URL, albumImage: URL, id: String, favorited: Bool) {
		self.songTitle = songTitle
		self.artist = artist
		self.shareCount = shareCount
		self.starCount = starCount
		self.songLength = songLength
		self.lyrics = lyrics
		self.backgroundImage = backgroundImage
		self.albumImage = albumImage
		self.id = id
		self.favorited = favorited
	}
}
