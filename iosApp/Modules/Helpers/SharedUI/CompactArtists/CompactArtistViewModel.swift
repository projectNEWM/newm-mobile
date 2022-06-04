import Foundation
import shared
import Utilities

public struct CompactArtistViewModel: Identifiable {
	public let image: URL?
	public let name: String
	public let numberOfSongs: String
	public let id: ObjectIdentifier
	
	public init(_ model: shared.Artist) {
		if let imageUrl = URL(string: model.image) {
			self.image = imageUrl
		} else {
			Log("bad artist image URL")
			image = nil
		}
		numberOfSongs = "5 songs"
		name = model.name
		id = model.id.objectIdentifier
	}
}

