import Foundation
import Combine
import shared
import AVFoundation
import ModuleLinker
import Resolver

class SongPlayingViewModel: ObservableObject {
	typealias Seconds = Int
	
	private var cancellables = Set<AnyCancellable>()
		
	@Injected private var audioPlayer: AudioPlayer
	
	@Published var song: SongInfo = MockData.songInfo()
	@Published var lyricsOffsetPercentage: Float = 0
	@Published var currentTime: String = 0.playbackTimeString
	@Published var totalTime: String = 0.playbackTimeString
	@Published var playbackState: PlaybackState = .paused
	@Published var showTipping: Bool = false
	@Published var error: Error?
	@Published var title: String = .nowPlayingTitle
	var percentPlayed: CGFloat = 0
	
	let supportArtistPrompt: String = .likeTheArtist
	
	init() {
		Task { @MainActor in
			for await currentTimeInt in audioPlayer.currentTime.values {
				currentTime = currentTimeInt.playbackTimeString
				percentPlayed = CGFloat(currentTimeInt) / CGFloat(audioPlayer.totalTime)
				totalTime = audioPlayer.totalTime.playbackTimeString
			}
		}
		
		Task { @MainActor in
			for await playbackState in audioPlayer.playbackState.values {
				self.playbackState = playbackState
			}
		}
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
		//TODO: move to KMM
		Task { @MainActor in
			do {
				try await audioPlayer.play(songId: "")
			} catch {
				self.error = error
			}
		}
	}
	
	func pauseTapped() {
		Task { await audioPlayer.pause() }
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

extension Int {
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
