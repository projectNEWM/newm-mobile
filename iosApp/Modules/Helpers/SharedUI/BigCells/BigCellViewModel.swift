import Foundation
import shared

public struct BigCellViewModel: Identifiable {
	public let image: URL?
	public let name: String
	public let genre: String
	public let id: String
}

public extension BigCellViewModel {
	init(artist: Artist) {
		if let imageUrl = URL(string: artist.image) {
			self.image = imageUrl
		} else {
			image = nil
		}
		name = artist.name
		genre = artist.genre
		id = artist.id
	}
	
	init(song: Song) {
		if let imageUrl = URL(string: song.image) {
			self.image = imageUrl
		} else {
			image = nil
		}
		name = song.title
		id = song.id
		genre = song.genre.title
	}
}
