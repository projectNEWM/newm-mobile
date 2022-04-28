import Foundation

enum MainViewModelTab: CaseIterable {
	case home
	case tribe
	case stars
	case wallet
	case more
	
	/// hacky - need to do this because the built in "More" tab doesn't work with SwiftUI.
	enum More: CaseIterable {
		case playlists
		case artists
		case profile
		case search
		case genres
	}
}

extension MainViewModelTab: CustomStringConvertible, Identifiable {
	var description: String {
		switch self {
		case .home: return "Home"
		case .tribe: return "Tribe"
		case .stars: return "Stars"
		case .wallet: return "Wallet"
		case .more: return "More"
		}
	}
	
	var id: ObjectIdentifier { description.objectIdentifier }
}

extension MainViewModelTab.More: CustomStringConvertible, Identifiable {
	var description: String {
		switch self {
		case .playlists: return "Playlists"
		case .artists: return "Artists"
		case .profile: return "Profile"
		case .search: return "Search"
		case .genres: return "Genres"
		}
	}
	
	var id: ObjectIdentifier { description.objectIdentifier }
}

extension MainViewModelTab: Hashable {
	func hash(into hasher: inout Hasher) {
		hasher.combine(description)
	}
}

extension MainViewModelTab.More: Hashable {
	func hash(into hasher: inout Hasher) {
		hasher.combine(description)
	}
}
