#if DEBUG
import Foundation
import shared
import Resolver
import ModuleLinker
import UIKit
import SwiftUI

public class MockData {
	public static var bigArtistCells: [BigCellViewModel] {
		models.map(BigCellViewModel.init)
	}
	
	public static var compactArtistCells: [CompactCellViewModel] {
		models.map(CompactCellViewModel.init)
	}

	public static func makeArtist(name: String, id: String) -> Artist {
		Artist(image: artistImageUrl, name: name, genre: "Rock", stars: 12000, id: id)
	}
	
	public static var models: [Artist] {
		[
			makeArtist(name: "David Bowie6asdlkfjsdlkfjBowie4Bow", id: "1"),
			makeArtist(name: "David Bowie2", id: "2"),
			makeArtist(name: "David Bowie3", id: "3"),
			makeArtist(name: "David Bowie6asdlkfjsdlkfjBowie4", id: "4"),
			makeArtist(name: "David Bowie5", id: "5"),
			makeArtist(name: "David Bowie6asdlkfjsdlkfj", id: "6"),
			makeArtist(name: "David Bowie6asdlkfjsdlkfjBowie4Bow", id: "7"),
			makeArtist(name: "David Bowie2", id: "8"),
			makeArtist(name: "David Bowie3", id: "9"),
			makeArtist(name: "David Bowie6asdlkfjsdlkfjBowie4", id: "10"),
			makeArtist(name: "David Bowie5", id: "11"),
			makeArtist(name: "David Bowie6asdlkfjsdlkfj", id: "12")
		]
	}
	
	public static var artistImage: UIImage {
		@Injected var imageProvider: TestImageProvider
		return imageProvider.image(for: .bowie)
	}
	
	public static var artistImageUrl: String {
		@Injected var imageProvider: TestImageProvider
		return imageProvider.url(for: .bowie)
	}

	public static var roundArtistImage: some View {
		Image(uiImage: artistImage)
			.circleImage(size: 60)
	}
	
	static func makeSong(title: String, isNFT: Bool = false) -> Song {
		Song(image: artistImageUrl, title: title, artist: makeArtist(name: "David Bowtie", id: "1"), isNft: isNFT, songId: title)
	}
	
	public static var songs: [Song] {
		[
			makeSong(title: "Song1"),
			makeSong(title: "Song2"),
			makeSong(title: "Song3", isNFT: true),
			makeSong(title: "Song4"),
			makeSong(title: "Song5"),
			makeSong(title: "Song6")
		]
	}
	
	static func makePlaylist(id: String) -> Playlist {
		Playlist(image: MockData.artistImageUrl, title: "Music for Gaming", creator: User(userName: "NEWM User"), songCount: 32, playlistId: id, genre: "Rock", starCount: 13, playCount: 439)
	}
	
	public static var playlists: [Playlist] {
		[
			makePlaylist(id: "1"),
			makePlaylist(id: "2"),
			makePlaylist(id: "3"),
			makePlaylist(id: "4"),
			makePlaylist(id: "5"),
			makePlaylist(id: "6"),
			makePlaylist(id: "7"),
			makePlaylist(id: "8"),
			makePlaylist(id: "9"),
		]
	}
}
#endif
