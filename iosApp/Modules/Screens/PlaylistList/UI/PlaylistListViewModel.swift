import Foundation
import UIKit.UIImage
import Resolver
import ModuleLinker
import Models

class PlaylistListViewModel: ObservableObject {
	@Injected private var playlistListUseCase: PlaylistListUseCaseProtocol
	
	@Published var playlists: [Playlist] = []

	init() {
		playlists = playlistListUseCase.execute().map(PlaylistListViewModel.Playlist.init)
	}
}

extension PlaylistListViewModel {
	struct Playlist: Identifiable {
		let image: URL?
		let title: String
		let creator: String
		let genre: String
		let starCount: String
		let playCount: String
		let playlistID: String
		var id: String { playlistID }
		
		init(image: URL?, title: String, creator: String, genre: String, starCount: String, playCount: String, playlistID: String) {
			self.image = image
			self.title = title
			self.creator = creator
			self.genre = genre
			self.starCount = starCount
			self.playCount = playCount
			self.playlistID = playlistID
		}
		
		init(_ playlist: Models.Playlist) {
			let imageUrl = URL(string: playlist.image)
			self.init(
				image: imageUrl,
				title: playlist.title,
				creator: playlist.creator.nickname ?? "Nick Name",
				genre: playlist.genre.title,
				starCount: "\(playlist.starCount) ✭",
				playCount: "\(playlist.playCount)",
				playlistID: playlist.playlistId
			)
		}
	}
}
