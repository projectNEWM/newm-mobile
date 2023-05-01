import Foundation

public struct Artist {
	public init(image: String, name: String, genre: String, stars: Int, id: String) {
		self.image = image
		self.name = name
		self.genre = genre
		self.stars = stars
		self.id = id
	}
	
	public let image: String
	public let name: String
	public let genre: String
	public let stars: Int
	public let id: String
}
