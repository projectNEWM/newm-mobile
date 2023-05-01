import Foundation

public struct Song: Equatable, Hashable {
	public init(image: String, title: String, artist: Artist, isNft: Bool, id: String, favorited: Bool, duration: Int, genre: Genre) {
		self.image = image
		self.title = title
		self.artist = artist
		self.isNft = isNft
		self.id = id
		self.favorited = favorited
		self.duration = duration
		self.genre = genre
	}
	
	public let image: String
	public let title: String
	public let artist: Artist
	public let isNft: Bool
	public let id: String
	public var favorited: Bool
	public var duration: Int
	public var genre: Genre
	
	public static func == (lhs: Song, rhs: Song) -> Bool {
		return lhs.id == rhs.id
	}
	
	public func hash(into hasher: inout Hasher) {
		hasher.combine(id)
	}
}
