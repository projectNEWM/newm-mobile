import Foundation
import shared
import Utilities

public struct BigArtistViewModel: Identifiable {
	public let image: URL?
	public let name: String
	public let genre: String
	public let artistID: String
	public var id: ObjectIdentifier { artistID.objectIdentifier }
	
	public init(_ model: shared.Artist) {
		if let imageUrl = URL(string: model.image) {
			self.image = imageUrl
		} else {
			Log("bad artist image URL")
			image = nil
		}
		name = model.name
		genre = model.genre
		artistID = model.id
	}
}
