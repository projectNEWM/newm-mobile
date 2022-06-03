#if DEBUG
import Foundation
import SwiftUI
import ModuleLinker
import Resolver
import shared
import SharedUI

class MockData {
	static var moreOfWhatYouLikeCells: [HomeViewModel.MoreOfWhatYouLike] {
		artists.map(HomeViewModel.MoreOfWhatYouLike.init)
	}
	
	static var artistCells: [HomeViewModel.Artist] {
		artists.map(HomeViewModel.Artist.init)
	}
	
	static func makeArtist(name: String, id: String) -> Artist {
		Artist(image: artistImageUrl, name: name, genre: "Rock", stars: 12000, id: id)
	}
	
	static var artists: [Artist] {
		[
			makeArtist(name: "David Bowie6asdlkfjsdlkfjBowie4Bow", id: "1"),
			makeArtist(name: "David Bowie2", id: "2"),
			makeArtist(name: "David Bowie3", id: "3"),
			makeArtist(name: "David Bowie6asdlkfjsdlkfjBowie4", id: "4"),
			makeArtist(name: "David Bowie5", id: "5"),
			makeArtist(name: "David Bowie6asdlkfjsdlkfj", id: "6")
		]
	}
	
	static var artistImage: UIImage {
		@Injected var imageProvider: TestImageProvider
		return imageProvider.image(for: .bowie)
	}
	
	static var artistImageUrl: String {
		@Injected var imageProvider: TestImageProvider
		return imageProvider.url(for: .bowie)
	}

	static var roundArtistImage: some View {
		Image(uiImage: artistImage)
			.circleImage(size: 60)
	}
	
	static func makeSong(title: String, isNFT: Bool = false) -> Song {
		Song(image: artistImageUrl, title: title, artist: makeArtist(name: "David Bowtie", id: "1"), isNft: isNFT, songId: title)
	}
	
	static var songs: [Song] {
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
	
	static var playlists: [Playlist] {
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

class MockGetArtistsUseCase: GetArtistsUseCase {
	func execute() -> [Artist] {
		MockData.artists
	}
}

class MockGetSongsUseCase: GetSongsUseCase {
	func execute() -> [Song] {
		MockData.songs
	}
}
#endif
