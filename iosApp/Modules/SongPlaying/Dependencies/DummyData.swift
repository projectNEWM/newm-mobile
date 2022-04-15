import Foundation
import UIKit
import SwiftUI
import Combine
import AVFAudio
import SharedUI

class DummyData {
	static var songPlayingViewModel: SongPlayingViewModel { SongPlayingViewModel(songInfoUseCase: songInfoUseCase, musicPlayerUseCase: musicPlayerUseCase)}
	
	static var songInfoUseCase: SongInfoUseCase { SongInfoUseCaseImpl() }
	static var musicPlayerUseCase: MusicPlayerUseCase { MusicPlayerUseCaseImpl() }
}

class SongInfoUseCaseImpl: SongInfoUseCase {
	func execute() -> SongPlayingViewModel.Song {
		SongPlayingViewModel.Song(
			songTitle: "Song Title",
			artistName: "Artist Name",
			shareCount: "543",
			starCount: "48",
			songLength: 200,
			lyrics: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
			backgroundImage: URL(string: "https://ychef.files.bbci.co.uk/976x549/p01j3jyb.jpg")!,
			albumImage: URL(string: "https://i.scdn.co/image/ab6761610000e5ebb78f77c5583ae99472dd4a49")!
		)
	}
}

class MusicPlayerUseCaseImpl: MusicPlayerUseCase {
	enum PlaybackState {
		case playing
		case paused
	}

	lazy var playbackTime: AnyPublisher<Int, Never> = $_playbackTime.eraseToAnyPublisher()
	@Published private var _playbackTime: Int = 0

	//TODO: MU: remove this stuff
	private var timer: AnyCancellable?
	private var playbackState: PlaybackState = .paused

	init() {
		//TODO: MU remove this
		timer = Timer.publish(every: 1, tolerance: nil, on: .current, in: .default, options: nil)
			.autoconnect()
			.filter { [weak self] _ in self?.playbackState == .playing }
			.sink { [weak self] _ in
				self?._playbackTime += 1
			}
	}

	func play() {
		playbackState = .playing
	}

	func pause() {
		playbackState = .paused
	}

	func stop() {
		pause()
		_playbackTime = 0
	}
}
