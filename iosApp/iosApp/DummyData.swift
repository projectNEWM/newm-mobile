//
//  DummyData.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/9/22.
//

import Foundation
import UIKit
import SwiftUI

class DummyData {
	static func makeArtist(name: String) -> HomeViewModel.Artist {
		HomeViewModel.Artist(image: artistImage.jpegData(compressionQuality: 1.0)!, name: name, genre: "Rock", stars: "✭ 12000", artistID: name)
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
		UIImage(named: "bowie")!
	}
	
	static var roundArtistImage: some View {
		CircleImage(image: artistImage, size: 60)
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
		HomeViewModel.Playlist(image: DummyData.artistImage, title: "Music for Gaming", creator: "NEWM", songCount: "♬ 32", playlistID: id)
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
	
	static func makePlaylistListPlaylist(id: String) -> PlaylistListViewModel.Playlist {
		PlaylistListViewModel.Playlist(image: DummyData.artistImage, title: "Music for Gaming", creator: "by NEWM", genre: "Uplifting", starCount: "☆ 12k", playCount: "▶️ 938k", playlistID: id)
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

class LoggedInUserUseCase: ObservableObject {
	static var shared = LoggedInUserUseCase()
	
	@Published var loggedInUser: User? = nil
	
	private init() {}
	
	func logIn() {
		loggedInUser = User(email: "mulrich@projectnewm.io")
	}
	
	func logOut() {
		loggedInUser = nil
	}
}
