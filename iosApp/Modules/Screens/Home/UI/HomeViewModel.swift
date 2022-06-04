import Foundation
import Combine
import Resolver
import ModuleLinker
import SwiftUI
import shared
import Utilities

enum HomeRoute {
	case artist(id: String)
	case songPlaying(id: String)
	case playlist(id: String)
	case allPlaylists
}

public class HomeViewModel: ObservableObject {
	@Injected private var artistsUseCase: GetArtistsUseCase
	@Injected private var mostPopularThisWeekUseCase: GetMostPopularThisWeekUseCase
	@Injected private var moreOfWhatYouLikeUseCase: GetMoreOfWhatYouLikeUseCase

	let moreOfWhatYouLikeTitle: String = .moreOfWhatYouLike
	let artistSectionTitle: String = .artists
	let mostPopularThisWeekTitle: String = .mostPopularThisWeek
	
	@Published var homeRoute: HomeRoute?

	@Published var moreOfWhatYouLikes: [HomeViewModel.BigArtist] = []
	@Published var newmArtists: [HomeViewModel.CompactArtist] = []
	@Published var mostPopularThisWeek: [HomeViewModel.BigArtist] = []
		
	init() {
		refresh()
	}
	
	func refresh() {
		newmArtists = artistsUseCase.execute().map(HomeViewModel.CompactArtist.init)
		mostPopularThisWeek = mostPopularThisWeekUseCase.execute().map(HomeViewModel.BigArtist.init)
		moreOfWhatYouLikes = moreOfWhatYouLikeUseCase.execute().map(HomeViewModel.BigArtist.init)
	}
}

extension HomeViewModel {
	struct BigArtist: Identifiable {
		let image: URL?
		let name: String
		let genre: String
		let artistID: String
		var id: ObjectIdentifier { artistID.objectIdentifier }
		
		init(_ model: shared.Artist) {
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
	
	struct CompactArtist: Identifiable {
		let image: URL?
		let name: String
		let numberOfSongs: String
		let id: ObjectIdentifier
		
		init(_ model: shared.Artist) {
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

	struct Song: Identifiable {
		let image: URL?
		let title: String
		let artist: String
		let isNFT: Bool
		let songID: String
		var id: ObjectIdentifier { songID.objectIdentifier }
		
		init(_ model: shared.Song) {
			if let imageUrl = URL(string: model.image) {
				self.image = imageUrl
			} else {
				Log("bad song image URL")
				image = nil
			}
			title = model.title
			artist = model.artist.name
			isNFT = model.isNft
			songID = model.songId
		}
	}
	
	struct Playlist: Identifiable {
		let image: URL?
		let title: String
		let creator: String
		let songCount: String
		let playlistID: String
		var id: ObjectIdentifier { playlistID.objectIdentifier }
		
		init(_ model: shared.Playlist) {
			if let imageUrl = URL(string: model.image) {
				self.image = imageUrl
			} else {
				Log("bad playlist image URL")
				image = nil
			}
			title = model.title
			creator = "by \(model.creator)"
			songCount = "\(model.songCount) plays"
			playlistID = model.playlistId
		}
	}
}
