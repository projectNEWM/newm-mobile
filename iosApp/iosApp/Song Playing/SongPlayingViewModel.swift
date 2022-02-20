import Foundation
import Combine
import shared

protocol SongInfoUseCase {
	func execute() -> SongPlayingViewModel.Song
}

protocol MusicPlayerUseCase {
	var playbackTime: AnyPublisher<Int, Never> { get }
}

class SongPlayingViewModel: ObservableObject {
	typealias Seconds = Int
	
	private var cancellables = Set<AnyCancellable>()
	
	private let songInfoUseCase: SongInfoUseCase
	private let musicPlayerUseCase: MusicPlayerUseCase
	
	@Published var song: Song
	@Published var lyricsOffsetPercentage: Float = 0
	@Published var playbackTime: String = SongPlayingViewModel.playbackTimePlaceholder
	@Published var playbackState: PlaybackState = .paused
	@Published var showTipping: Bool = false
	
	let supportArtistPrompt: String = NSLocalizedString("LIKE_THE_SONG_SUPPORT_THE_ARTIST", comment: "")
	
	init(songInfoUseCase: SongInfoUseCase, musicPlayerUseCase: MusicPlayerUseCase) {
		self.songInfoUseCase = songInfoUseCase
		self.musicPlayerUseCase = musicPlayerUseCase
		
		song = songInfoUseCase.execute()
		
		musicPlayerUseCase.playbackTime
			.map(\.playbackTimeString)
			.sink { [weak self] in self?.playbackTime = $0 }
			.store(in: &cancellables)
	}
	
	func supportArtistTapped() {
		showTipping = true
	}
	
	func previousSongTapped() {
		
	}
	
	func nextSongTapped() {
		
	}
	
	func repeatTapped() {
		
	}
	
	func shuffleTapped() {
		
	}
	
	func playTapped() {
		playbackState = .playing
	}
	
	func pauseTapped() {
		playbackState = .paused
	}
	
	func airplayTapped() {
		
	}
	
	func shareTapped() {
		
	}
	
	func starTapped() {
		
	}
	
	func tipTapped(_ amount: TipAmount) {
		showTipping = false
	}
}

extension SongPlayingViewModel {
	struct Song {
		let songTitle: String
		let artistName: String
		let shareCount: String
		let starCount: String
		let songLength: Seconds
		let lyrics: String
		let backgroundImage: URL
		let albumImage: URL
	}
}

private extension SongPlayingViewModel {
	static var playbackTimePlaceholder: String { "--:--" }
}

extension SongPlayingViewModel {
	enum PlaybackState {
		case playing
		case paused
	}
}

private extension Int {
	var playbackTimeString: String {
		let formatter = DateComponentsFormatter()
		formatter.allowedUnits = [.hour, .minute, .second]
		formatter.unitsStyle = .positional
		guard let playbackTime = formatter.string(from: TimeInterval(self)) else {
			LoggerKt.printLogD(className: String(describing: Self.self), message: "badPlaybackTimeFormat")
			return SongPlayingViewModel.playbackTimePlaceholder
		}
		
		return playbackTime
	}
}
