import Foundation

public struct Playlist {
	public init(image: String, title: String, creator: User, songCount: Int, playlistId: String, genre: Genre, starCount: Int, playCount: Int) {
		self.image = image
		self.title = title
		self.creator = creator
		self.songCount = songCount
		self.playlistId = playlistId
		self.genre = genre
		self.starCount = starCount
		self.playCount = playCount
	}
	
    public let image: String
    public let title: String
    public let creator: User
    public let songCount: Int
    public let playlistId: String
    public let genre: Genre
    public let starCount: Int
    public let playCount: Int
}
