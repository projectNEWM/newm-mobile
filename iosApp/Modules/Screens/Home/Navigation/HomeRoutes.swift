import Foundation
import shared

enum HomeRoute {
	case artist(id: String)
	case songPlaying
	case playlist(id: String)
	case allPlaylists
}
