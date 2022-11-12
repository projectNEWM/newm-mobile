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

extension Genre: CaseIterable {
	public static var allCases: [Genre] {
		[
			.folk,
			.classical,
			.country,
			.rap,
			.rock
		]
	}
	
	var title: String {
		switch self {
		case .folk: return "Folk"
		case .classical: return "Classical"
		case .country: return "Country"
		case .rap: return "Rap"
		case .rock: return "Rock"
		default: return "Genre"
		}
	}
}
