import Foundation
import Combine
import Resolver
import ModuleLinker
//TODO: Remove UIImage uses and replace with URLs
import UIKit.UIImage
import SwiftUI

protocol GetArtistsUseCase {
	func execute() async -> [HomeViewModel.Artist]
}

protocol GetSongsUseCase {
	func execute() async -> [HomeViewModel.Song]
}

protocol GetPlaylistsUseCase {
	func execute() async -> [HomeViewModel.Playlist]
}

public class HomeViewModel: ObservableObject {
	enum Section: CaseIterable {
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

	@Published var selectedSection: Section = .allCases.first!
	
	var artists: [HomeViewModel.Artist] = []
	var songs: [HomeViewModel.Song] = []
	var playlists: [HomeViewModel.Playlist] = []
	
	let sectionTitles = Section.allCases.map(\.description)
	
	init() {
		Task.detached { [weak self] in
			guard let self = self else { return }
			await self.refresh()
		}
	}
	
	func refresh() async {
		artists = await artistsUseCase.execute()
		songs = await songsUseCase.execute()
		playlists = await playlistsUseCase.execute()
	}
}

extension HomeViewModel {
	struct Artist: Identifiable {
		let image: Data
		let name: String
		let genre: String
		let stars: String
		let artistID: String
		var id: ObjectIdentifier { artistID.objectIdentifier }
	}

	struct Song: Identifiable {
		let image: UIImage
		let title: String
		let artist: String
		let isNFT: Bool
		let songID: String
		var id: ObjectIdentifier { title.objectIdentifier }
	}
	
	struct Playlist: Identifiable {
		let image: UIImage
		let title: String
		let creator: String
		let songCount: String
		let playlistID: String
		var id: ObjectIdentifier { playlistID.objectIdentifier }
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
