import Foundation
import shared

enum HomeRoute {
	case artist(id: String)
	case songPlaying(song: Song)
	case playlist(id: String)
	case allPlaylists
}
