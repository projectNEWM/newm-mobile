#if DEBUG
import Foundation
import ModuleLinker
import Resolver
import Models

class MockData {
	static func makePlaylist(id: String) -> Playlist {
		Playlist(image: MockData.artistImageUrl, title: "Music for Gaming", creator: User(userName: "NEWM User"), songCount: 32, playlistId: id, genre: .rock, starCount: 13, playCount: 439)
	}
	
	static var artistImageUrl: String {
		""
	}
}
#endif
