import Foundation
import Combine
//TODO: Remove UIImage uses and replace with URLs
import UIKit.UIImage

class HomeViewModel: ObservableObject {
	enum Section: CaseIterable {
		case explore
		case ambient
		case hipHop
		case alternative
	}
	
	@Published var title = NSLocalizedString("NEWM", comment: "")
	
	@Published var artistSectionTitle: String = NSLocalizedString("NEWM_ARTISTS", comment: "")
	@Published var artists: [HomeViewModel.Artist] = DummyData.artists
	@Published var selectedArtist: HomeViewModel.Artist? = nil
	
	@Published var songsSectionTitle = NSLocalizedString("NEWM_SONGS", comment: "")
	@Published var songs: [HomeViewModel.Song] = DummyData.songs
	@Published var selectedSong: HomeViewModel.Song? = nil
	
	@Published var playlistsSectionTitle = NSLocalizedString("CURATED_PLAYLISTS", comment: "")
	@Published var playlists: [HomeViewModel.Playlist] = DummyData.playlists
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
		case .alternative: return Localizable.localize("ALTERNATIVE")
		case .ambient: return Localizable.localize("AMBIENT")
		case .explore: return Localizable.localize("EXPLORE")
		case .hipHop: return Localizable.localize("HIP_HOP")
		}
	}
}
