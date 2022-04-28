import Foundation
import Combine
import Resolver
import ModuleLinker
//TODO: Remove UIImage uses and replace with URLs
import UIKit.UIImage

public class HomeViewModel: ObservableObject {
	enum Section: CaseIterable {
		case explore
		case ambient
		case hipHop
		case alternative
	}
	
	@Published var title: String = .newm
	
	@Published var artistSectionTitle: String = .newmArtists
	@Injected var artists: [HomeViewModel.Artist]
	@Published var selectedArtist: HomeViewModel.Artist? = nil
	
	@Published var songsSectionTitle: String = .newmSongs
	@Published var songs: [HomeViewModel.Song] = Resolver.resolve()
	@Published var selectedSong: HomeViewModel.Song? = nil
	
	@Published var playlistsSectionTitle: String = .curatedPlaylists
	@Published var playlists: [HomeViewModel.Playlist] = Resolver.resolve()
	@Published var selectedPlaylist: HomeViewModel.Playlist? = nil

	@Published var selectedSectionIndex: Int = 0
	let sections = Section.allCases.map(\.description)
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

extension HomeViewModel {
	func deselectAll() {
		selectedArtist = nil
		selectedSong = nil
		selectedPlaylist = nil
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
