import Foundation
import Combine
import Resolver
import ModuleLinker
//TODO: Remove UIImage uses and replace with URLs
import UIKit.UIImage
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
	enum Section: Int, CaseIterable {
		case explore
		case ambient
		case hipHop
		case alternative
	}
	
	@Injected private var artistsUseCase: GetArtistsUseCase
	@Injected private var songsUseCase: GetSongsUseCase
	@Injected private var playlistsUseCase: GetPlaylistsUseCase
	
	let title: String = .newm
	let artistSectionTitle: String = .newmArtists
	let songsSectionTitle: String = .newmSongs
	let playlistsSectionTitle: String = .curatedPlaylists

	@Published var selectedSection: Int = 0
	
	@Published var homeRoute: HomeRoute?

	var artists: [HomeViewModel.Artist] = []
	var songs: [HomeViewModel.Song] = []
	var playlists: [HomeViewModel.Playlist] = []
	
	let sectionTitles = Section.allCases.map(\.description)
	
	init() {
		refresh()
	}
	
	func refresh() {
		artists = artistsUseCase.execute().map(HomeViewModel.Artist.init)
		songs = songsUseCase.execute().map(HomeViewModel.Song.init)
		playlists = playlistsUseCase.execute().map(HomeViewModel.Playlist.init)
	}
}

extension HomeViewModel {
	struct Artist: Identifiable {
		let image: URL?
		let name: String
		let genre: String
		let stars: String
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
			stars = "\(model.stars) ✭"
			artistID = model.id
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

extension HomeViewModel.Section: CustomStringConvertible {
	var description: String {
		switch self {
		case .alternative: return .alternative
		case .ambient: return .ambient
		case .explore: return .explore
		case .hipHop: return .hipHop
		}
	}
}
