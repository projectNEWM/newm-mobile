import Foundation
import UIKit.UIImage
import Resolver
import ModuleLinker

class PlaylistListViewModel: ObservableObject {
	let playlists: [Playlist]

	init(id: String) {
		playlists = Resolver.resolve(args: id)
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
