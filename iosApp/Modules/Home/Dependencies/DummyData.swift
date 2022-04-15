import Foundation
import SwiftUI
import SharedUI

class DummyData {
	static func makeArtist(name: String) -> HomeViewModel.Artist {
		HomeViewModel.Artist(image: artistImage.jpegData(compressionQuality: 1.0)!, name: name, genre: "Rock", stars: "✭ 12k", artistID: name)
	}
	
	static var artists: [HomeViewModel.Artist] {
		[
			makeArtist(name: "David Bow"),
			makeArtist(name: "David Bowie2"),
			makeArtist(name: "David Bowie3"),
			makeArtist(name: "David Bowie4"),
			makeArtist(name: "David Bowie5"),
			makeArtist(name: "David Bowie6asdlkfjsdlkfj")
		]
	}
	
	static var artistImage: UIImage {
		.Bowie
	}
	
	static var roundArtistImage: some View {
		CircleImage(artistImage, size: 60)
	}
	
	static func makeSong(title: String, isNFT: Bool = false) -> HomeViewModel.Song {
		HomeViewModel.Song(image: artistImage, title: title, artist: "Artist", isNFT: isNFT, songID: title)
	}
	
	static var songs: [HomeViewModel.Song] {
		[
			makeSong(title: "Song1"),
			makeSong(title: "Song2"),
			makeSong(title: "Song3", isNFT: true),
			makeSong(title: "Song4"),
			makeSong(title: "Song5"),
			makeSong(title: "Song6")
		]
	}
	
	static func makeHomePlaylist(id: String) -> HomeViewModel.Playlist {
		HomeViewModel.Playlist(image: DummyData.artistImage, title: "Music for Gaming", creator: "by NEWM", songCount: "♬ 32", playlistID: id)
	}
	
	static var playlists: [HomeViewModel.Playlist] {
		[
			makeHomePlaylist(id: "1"),
			makeHomePlaylist(id: "2"),
			makeHomePlaylist(id: "3"),
			makeHomePlaylist(id: "4"),
			makeHomePlaylist(id: "5"),
			makeHomePlaylist(id: "6"),
			makeHomePlaylist(id: "7"),
			makeHomePlaylist(id: "8"),
			makeHomePlaylist(id: "9")
		]
	}
}
