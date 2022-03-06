import Foundation

class MainViewModel: ObservableObject {
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
	
	@Published var selectedTab: Tab = .home
}
