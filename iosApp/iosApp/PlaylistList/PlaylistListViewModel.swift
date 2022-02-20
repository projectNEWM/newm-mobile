import Foundation
import UIKit.UIImage

class PlaylistListViewModel: ObservableObject {
	let id: String
	@Published var playlists: [Playlist] = DummyData.playlistListPlaylists

	init(id: String) {
		self.id = id
	}
}

extension PlaylistListViewModel {
	struct Playlist: Identifiable {
		let image: UIImage
		let title: String
		let creator: String
		let genre: String
		let starCount: String
		let playCount: String
		let playlistID: String
		var id: ObjectIdentifier { playlistID.objectIdentifier }
	}
}
