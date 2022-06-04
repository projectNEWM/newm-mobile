import Foundation
import Combine
import Resolver
import ModuleLinker
import SwiftUI
import shared
import Utilities
import SharedUI

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

	@Published var moreOfWhatYouLikes: [BigArtistViewModel] = []
	@Published var newmArtists: [CompactArtistViewModel] = []
	@Published var mostPopularThisWeek: [BigArtistViewModel] = []
		
	init() {
		refresh()
	}
	
	func refresh() {
		newmArtists = artistsUseCase.execute().map(CompactArtistViewModel.init)
		mostPopularThisWeek = mostPopularThisWeekUseCase.execute().map(BigArtistViewModel.init)
		moreOfWhatYouLikes = moreOfWhatYouLikeUseCase.execute().map(BigArtistViewModel.init)
	}
}

extension HomeViewModel {	
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
