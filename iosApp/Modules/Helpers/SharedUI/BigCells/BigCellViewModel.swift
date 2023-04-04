import Foundation
import shared
import Resolver
import ModuleLinker

public struct BigCellViewModel: Identifiable {
	public let image: URL?
	public let name: String
	public let genre: String
	public let id: String
	public let onTap: () -> ()
}

public extension BigCellViewModel {
	init(artist: Artist, onTap: @escaping () -> ()) {
		if let imageUrl = URL(string: artist.image) {
			self.image = imageUrl
		} else {
			image = nil
		}
		name = artist.name
		genre = artist.genre
		id = artist.id
		self.onTap = onTap
	}
}

public extension BigCellViewModel {
	init(song: Song, onTap: @escaping () -> ()) {
		if let imageUrl = URL(string: song.image) {
			self.image = imageUrl
		} else {
			image = nil
		}
		name = song.title
		id = song.id
		genre = song.genre.title
		self.onTap = onTap
	}
}
