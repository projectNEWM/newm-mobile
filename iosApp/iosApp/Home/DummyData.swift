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
	static func makeNewmArtist(name: String) -> NewmArtist {
		NewmArtist(image: newmArtistImage.jpegData(compressionQuality: 1.0)!, name: name, genre: "Rock", stars: 12000)
	}
	
	static var newmArtists: [NewmArtist] {
		[
			makeNewmArtist(name: "David Bow"),
			makeNewmArtist(name: "David Bowie2"),
			makeNewmArtist(name: "David Bowie3"),
			makeNewmArtist(name: "David Bowie4"),
			makeNewmArtist(name: "David Bowie5"),
			makeNewmArtist(name: "David Bowie6asdlkfjsdlkfj")
		]
	}
	
	static var newmArtistImage: UIImage {
		UIImage(named: "bowie")!
	}
	
	static var roundArtistImage: some View {
		Image.roundImage(newmArtistImage, size: 60)
	}
	
	static func makeNewmSong(title: String) -> NewmSong {
		NewmSong(image: newmArtistImage.jpegData(compressionQuality: 1.0)!, title: title, artist: "Artist")
	}
	
	static var newmSongs: [NewmSong] {
		[
			makeNewmSong(title: "Song1"),
			makeNewmSong(title: "Song2"),
			makeNewmSong(title: "Song3"),
			makeNewmSong(title: "Song4"),
			makeNewmSong(title: "Song5"),
			makeNewmSong(title: "Song6")
		]
	}
}
