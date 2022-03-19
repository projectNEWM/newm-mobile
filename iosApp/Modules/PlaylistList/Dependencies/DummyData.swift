import Foundation
import UIKit

class DummyData {
	static var artistImage: UIImage {
		.Bowie
	}
	
	static func makePlaylistListPlaylist(id: String) -> PlaylistListViewModel.Playlist {
		PlaylistListViewModel.Playlist(image: DummyData.artistImage, title: "Music for Gaming", creator: "by NEWM", genre: "Uplifting", starCount: "✭ 12k", playCount: "▶ 938k", playlistID: id)
	}
	
	static var playlistListPlaylists: [PlaylistListViewModel.Playlist] {
		[
			makePlaylistListPlaylist(id: "1"),
			makePlaylistListPlaylist(id: "2"),
			makePlaylistListPlaylist(id: "3"),
			makePlaylistListPlaylist(id: "4"),
			makePlaylistListPlaylist(id: "5"),
			makePlaylistListPlaylist(id: "6"),
			makePlaylistListPlaylist(id: "7"),
			makePlaylistListPlaylist(id: "8"),
			makePlaylistListPlaylist(id: "9")
		]
	}
}
