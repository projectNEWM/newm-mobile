import Foundation
import shared
import Utilities

public struct BigCellViewModel: Identifiable {
	public let image: URL?
	public let name: String
	public let genre: String
	public let artistID: String
	public var id: ObjectIdentifier { artistID.objectIdentifier }
}

public extension BigCellViewModel {
	init(artist: Artist) {
		if let imageUrl = URL(string: artist.image) {
			self.image = imageUrl
		} else {
			Log("bad artist image URL")
			image = nil
		}
		name = artist.name
		genre = artist.genre
		artistID = artist.id
	}
	
	init(song: Song) {
		if let imageUrl = URL(string: song.image) {
			self.image = imageUrl
		} else {
			Log("bad song image URL")
			image = nil
		}
		name = song.title
		artistID = song.songId
		genre = song.genre.title
	}
}
