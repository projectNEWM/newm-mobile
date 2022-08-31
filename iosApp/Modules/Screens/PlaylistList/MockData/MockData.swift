#if DEBUG
import Foundation
import ModuleLinker
import Resolver
import shared

class MockData {
	static func makePlaylist(id: String) -> shared.Playlist {
		Playlist(image: MockData.artistImageUrl, title: "Music for Gaming", creator: User(userName: "NEWM User", id: "1"), songCount: 32, playlistId: id, genre: "Rock", starCount: 13, playCount: 439)
	}
	
	static var artistImageUrl: String {
		@Injected var imageProvider: TestImageProvider
		return imageProvider.url(for: .bowie)
	}
}
#endif
