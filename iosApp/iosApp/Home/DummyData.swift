//
//  DummyData.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/9/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import UIKit
import SwiftUI

class DummyData {
	static func makeArtist(name: String) -> ArtistCell.Artist {
		ArtistCell.Artist(image: artistImage.jpegData(compressionQuality: 1.0)!, name: name, genre: "Rock", stars: 12000, artistID: name)
	}
	
	static var artists: [ArtistCell.Artist] {
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
	
	static func makeSong(title: String, isNFT: Bool = false) -> SongCell.Song {
		SongCell.Song(image: artistImage, title: title, artist: "Artist", isNFT: isNFT, songID: title)
	}
	
	static var songs: [SongCell.Song] {
		[
			makeSong(title: "Song1"),
			makeSong(title: "Song2"),
			makeSong(title: "Song3", isNFT: true),
			makeSong(title: "Song4"),
			makeSong(title: "Song5"),
			makeSong(title: "Song6")
		]
	}
	
	static func makePlaylist(id: String) -> PlaylistCell.Playlist {
		PlaylistCell.Playlist(image: DummyData.artistImage, title: "Music for Gaming", creator: "NEWM", songCount: 32, playlistID: id)
	}
	
	static var playlists: [PlaylistCell.Playlist] {
		[
			makePlaylist(id: "1"),
			makePlaylist(id: "2"),
			makePlaylist(id: "3"),
			makePlaylist(id: "4"),
			makePlaylist(id: "5"),
			makePlaylist(id: "6"),
			makePlaylist(id: "7"),
			makePlaylist(id: "8"),
			makePlaylist(id: "9")
		]
	}
}

