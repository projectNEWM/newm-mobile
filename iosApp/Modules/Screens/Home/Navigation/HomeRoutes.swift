import Foundation
import shared

enum HomeRoute {
	case artist(id: String)
	case nowPlaying
	case playlist(id: String)
	case allPlaylists
}
