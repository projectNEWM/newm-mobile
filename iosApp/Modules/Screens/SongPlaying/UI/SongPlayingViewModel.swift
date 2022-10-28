import Foundation
import Combine
import shared
import AVFoundation
import ModuleLinker
import Resolver

class SongPlayingViewModel: ObservableObject {
	typealias Seconds = Int
	
	private var cancellables = Set<AnyCancellable>()
	
	private let songInfoUseCase: SongInfoUseCaseProtocol
	private let musicPlayerUseCase: MusicPlayerUseCaseProtocol
	
	@Published var song: SongInfo
	@Published var lyricsOffsetPercentage: Float = 0
	@Published var playbackTime: String = SongPlayingViewModel.playbackTimePlaceholder
	@Published var playbackState: PlaybackState = .paused
	@Published var showTipping: Bool = false
	
	let supportArtistPrompt: String = .likeTheArtist
	
	init(songInfoUseCase: SongInfoUseCaseProtocol, musicPlayerUseCase: MusicPlayerUseCaseProtocol) {
		self.songInfoUseCase = songInfoUseCase
		self.musicPlayerUseCase = musicPlayerUseCase
		
		song = songInfoUseCase.execute()
		
		musicPlayerUseCase.playbackTime
			.map(\.playbackTimeString)
			.sink { [weak self] in self?.playbackTime = $0 }
			.store(in: &cancellables)
		
		musicPlayerUseCase.playbackState
			.map(SongPlayingViewModel.PlaybackState.init)
			.sink { [weak self] in self?.playbackState = $0 }
			.store(in: &cancellables)
	}
}

extension SongPlayingViewModel {
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
		musicPlayerUseCase.play()
	}
	
	func pauseTapped() {
		playbackState = .paused
		musicPlayerUseCase.pause()
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

private extension SongPlayingViewModel {
	static var playbackTimePlaceholder: String { "--:--" }
}

extension SongPlayingViewModel {
	enum PlaybackState {
		case playing
		case paused
		
		init(_ playbackState: ModuleLinker.PlaybackState) {
			switch playbackState {
			case .playing: self = .playing
			case .paused: self = .paused
			}
		}
	}
}

private extension Int {
	private static let formatter = DateComponentsFormatter()

	var playbackTimeString: String {
		Int.formatter.allowedUnits = self > 3600 ? [.hour, .minute, .second] : [.minute, .second]
		Int.formatter.zeroFormattingBehavior = .pad
		guard let playbackTime = Int.formatter.string(from: TimeInterval(self)) else {
			//TODO:MU: Uncomment when KMM module added back
			return SongPlayingViewModel.playbackTimePlaceholder
		}
		
		return playbackTime
	}
}
