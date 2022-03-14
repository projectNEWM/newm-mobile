import Foundation
import UIKit
import SwiftUI
import Combine
import AVFAudio
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
	
//	static func makePlaylistListPlaylist(id: String) -> PlaylistListViewModel.Playlist {
//		PlaylistListViewModel.Playlist(image: DummyData.artistImage, title: "Music for Gaming", creator: "by NEWM", genre: "Uplifting", starCount: "✭ 12k", playCount: "▶ 938k", playlistID: id)
//	}
	
//	static var playlistListPlaylists: [PlaylistListViewModel.Playlist] {
//		[
//			makePlaylistListPlaylist(id: "1"),
//			makePlaylistListPlaylist(id: "2"),
//			makePlaylistListPlaylist(id: "3"),
//			makePlaylistListPlaylist(id: "4"),
//			makePlaylistListPlaylist(id: "5"),
//			makePlaylistListPlaylist(id: "6"),
//			makePlaylistListPlaylist(id: "7"),
//			makePlaylistListPlaylist(id: "8"),
//			makePlaylistListPlaylist(id: "9")
//		]
//	}
	
//	static var songPlayingViewModel: SongPlayingViewModel { SongPlayingViewModel(songInfoUseCase: songInfoUseCase, musicPlayerUseCase: musicPlayerUseCase)}
	
//	static var songInfoUseCase: SongInfoUseCase { SongInfoUseCaseImpl() }
//	static var musicPlayerUseCase: MusicPlayerUseCase { MusicPlayerUseCaseImpl() }
}

//class SongInfoUseCaseImpl: SongInfoUseCase {
//	func execute() -> SongPlayingViewModel.Song {
//		SongPlayingViewModel.Song(
//			songTitle: "Song Title",
//			artistName: "Artist Name",
//			shareCount: "543",
//			starCount: "48",
//			songLength: 200,
//			lyrics: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
//			backgroundImage: URL(string: "https://ychef.files.bbci.co.uk/976x549/p01j3jyb.jpg")!,
//			albumImage: URL(string: "https://i.scdn.co/image/ab6761610000e5ebb78f77c5583ae99472dd4a49")!
//		)
//	}
//}

//class MusicPlayerUseCaseImpl: MusicPlayerUseCase {
//	enum PlaybackState {
//		case playing
//		case paused
//	}
//
//	lazy var playbackTime: AnyPublisher<Int, Never> = $_playbackTime.eraseToAnyPublisher()
//	@Published private var _playbackTime: Int = 0
//
//	//TODO: MU: remove this stuff
//	private var timer: AnyCancellable?
//	private var playbackState: PlaybackState = .paused
//
//	init() {
//		//TODO: MU remove this
//		timer = Timer.publish(every: 1, tolerance: nil, on: .current, in: .default, options: nil)
//			.autoconnect()
//			.filter { [weak self] _ in self?.playbackState == .playing }
//			.sink { [weak self] _ in
//				self?._playbackTime += 1
//			}
//	}
//
//	func play() {
//		playbackState = .playing
//	}
//
//	func pause() {
//		playbackState = .paused
//	}
//
//	func stop() {
//		pause()
//		_playbackTime = 0
//	}
//}

//class LoggedInUserUseCase: ObservableObject {
//	static var shared = LoggedInUserUseCase()
//	
//	@Published var loggedInUser: User? = nil
//	
//	private init() {}
//	
//	func logIn() {
//		loggedInUser = User(email: "mulrich@projectnewm.io")
//	}
//	
//	func logOut() {
//		loggedInUser = nil
//	}
//}
