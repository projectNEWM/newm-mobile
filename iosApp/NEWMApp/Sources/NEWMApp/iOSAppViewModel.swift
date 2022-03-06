import Foundation
import Combine
import Strings

class iOSAppViewModel: ObservableObject {
	private var cancellables = [AnyCancellable]()
	@Published var loggedInUser: User? = nil
	@Published var selectedTab: Tab = .home

	init() {
//		LoggedInUserUseCase.shared.$loggedInUser.sink { [weak self] in
//			self?.loggedInUser = $0
//		}.store(in: &cancellables)
	}
}

extension iOSAppViewModel {
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

extension iOSAppViewModel.Tab: CustomStringConvertible, Identifiable {
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

extension iOSAppViewModel.MoreTab: CustomStringConvertible, Identifiable {
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

extension iOSAppViewModel.Tab: Hashable {
	func hash(into hasher: inout Hasher) {
		hasher.combine(description)
	}
}

extension iOSAppViewModel.MoreTab: Hashable {
	func hash(into hasher: inout Hasher) {
		hasher.combine(description)
	}
}


struct User {
	let email: String
}
