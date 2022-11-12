//#if DEBUG
//import Foundation
//import ModuleLinker
//import Resolver
//import UIKit
//import SwiftUI
//import Combine
//import AVFAudio
//import SharedUI
//import shared
//
//class MockData {
//	static func songInfo() -> Song {
////		shared.Song(image: "https://i.scdn.co/image/ab6761610000e5ebb78f77c5583ae99472dd4a49",
////					title: "Get Schwifty",
////					artist: SharedUI.MockData.makeArtist(name: "Rick Sanchez"),
////					isNft: false,
////					songId: "1",
////					favorited: false,
////					duration: 124)
////		(
////			songTitle: "Get Schwifty",
////			artist: SharedUI.MockData.makeArtist(name: "Rick Sanchez", id: "1"),
////			shareCount: "543",
////			starCount: "48",
////			songLength: 0,//get from audio player
////			lyrics: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
////			backgroundImage: URL(string: "https://ychef.files.bbci.co.uk/976x549/p01j3jyb.jpg")!,
////			albumImage: URL(string: "https://i.scdn.co/image/ab6761610000e5ebb78f77c5583ae99472dd4a49")!,
////			id: "1",
////			favorited: false
////		)
//	}
//}
//
////class MockMusicPlayerUseCase: MusicPlayerUseCaseProtocol {
////	enum PlaybackState {
////		case playing
////		case paused
////	}
////
////	lazy var playbackTime: AnyPublisher<Int, Never> = $_playbackTime.eraseToAnyPublisher()
////	@Published private var _playbackTime: Int = 0
////
////	//TODO: MU: remove this stuff
////	private var timer: AnyCancellable?
////	@Published private var _playbackState: ModuleLinker.PlaybackState = .paused
////	var playbackState: AnyPublisher<ModuleLinker.PlaybackState, Never> { $_playbackState.eraseToAnyPublisher() }
////
////	required init(id: String) {
////		timer = Timer.publish(every: 1, tolerance: nil, on: .current, in: .default, options: nil)
////			.autoconnect()
////			.filter { [weak self] _ in self?._playbackState == .playing }
////			.sink { [weak self] _ in
////				self?._playbackTime += 1
////			}
////	}
////
////	func play() {
////		_playbackState = .playing
////	}
////
////	func pause() {
////		_playbackState = .paused
////	}
////
////	func stop() {
////		pause()
////		_playbackTime = 0
////	}
////}
//#endif
