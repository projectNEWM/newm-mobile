import Foundation

enum HomeRoute {
	case artist(id: String)
	case songPlaying(id: String)
	case playlist(id: String)
	case allPlaylists
}
