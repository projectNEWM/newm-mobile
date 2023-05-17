import Foundation

enum HomeRoute: Hashable {
	case artist(id: String)
	case playlist(id: String)
	case allPlaylists
}
