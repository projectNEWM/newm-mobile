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
