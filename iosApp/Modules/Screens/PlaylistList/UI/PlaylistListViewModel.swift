import Foundation
import UIKit.UIImage
import Resolver
import ModuleLinker

class PlaylistListViewModel: ObservableObject {
	@Injected private var playlistListUseCase: PlaylistListUseCaseProtocol
	
	@Published var playlists: [Playlist] = []

	init() {
		playlists = playlistListUseCase.execute().map(PlaylistListViewModel.Playlist.init)
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
		
		init(image: UIImage, title: String, creator: String, genre: String, starCount: String, playCount: String, playlistID: String) {
			self.image = image
			self.title = title
			self.creator = creator
			self.genre = genre
			self.starCount = starCount
			self.playCount = playCount
			self.playlistID = playlistID
		}
		
		init(_ playlist: ModuleLinker.Playlist) {
			self.init(
				image: playlist.image,
				title: playlist.title,
				creator: playlist.creator,
				genre: playlist.genre,
				starCount: playlist.starCount,
				playCount: playlist.playCount,
				playlistID: playlist.playlistID
			)
		}
	}
}
