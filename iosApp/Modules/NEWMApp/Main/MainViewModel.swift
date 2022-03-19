import Foundation
import Combine
import Strings
import Login

class MainViewModel: ObservableObject {
	private var cancellables = [AnyCancellable]()
	@Published var selectedTab: Tab = .home

	init() {}
}

extension MainViewModel {
	enum Tab: CaseIterable {
		case home
		case tribe
		case stars
		case wallet
		case more
	}
	
	enum MoreTab: CaseIterable {
		case playlists
		case artists
		case profile
		case search
		case genres
	}
}

extension MainViewModel.Tab: CustomStringConvertible, Identifiable {
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

extension MainViewModel.MoreTab: CustomStringConvertible, Identifiable {
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

extension MainViewModel.Tab: Hashable {
	func hash(into hasher: inout Hasher) {
		hasher.combine(description)
	}
}

extension MainViewModel.MoreTab: Hashable {
	func hash(into hasher: inout Hasher) {
		hasher.combine(description)
	}
}
