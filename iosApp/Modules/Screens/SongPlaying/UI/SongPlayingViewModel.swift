import Foundation
import Combine
import shared
import AVFoundation
import ModuleLinker
import Resolver

@MainActor
class SongPlayingViewModel: ObservableObject {
	typealias Seconds = Int
	
	private var cancellables = Set<AnyCancellable>()
	
	@Published private var audioPlayer = Resolver.resolve((any AudioPlayer).self)
	
	@Published var song: SongInfo = MockData.songInfo()
	@Published var lyricsOffsetPercentage: Float = 0
	@Published var showTipping: Bool = false
	@Published var error: Error?
	@Published var title: String = .nowPlayingTitle
	
	var currentTime: String { audioPlayer.currentTime.playbackTimeString }
	var totalTime: String { audioPlayer.totalTime.playbackTimeString }
	var playbackState: PlaybackState { audioPlayer.playbackState }
	var percentPlayed: CGFloat { CGFloat(audioPlayer.currentTime) / CGFloat(audioPlayer.totalTime) }
	
	let supportArtistPrompt: String = .likeTheArtist
	
	nonisolated
	init() {}
}

extension SongPlayingViewModel {
	func supportArtistTapped() {
		showTipping = true
	}
	
	func prevTapped() {
		
	}
	
	func nextTapped() {
		
	}
	
	func repeatTapped() {
		
	}
	
	func shuffleTapped() {
		
	}
	
	func favoriteTapped() {
		
	}
	
	func playPauseTapped() {
		switch playbackState {
		case .playing:
			audioPlayer.playbackState = .paused
		case .stopped:
			audioPlayer.setSongId("")
			audioPlayer.playbackState = .playing
		case .paused:
			audioPlayer.playbackState = .playing
		}
	}
	
	func tipTapped(_ amount: TipAmount) {
		showTipping = false
	}
}

private extension SongPlayingViewModel {
	nonisolated static var playbackTimePlaceholder: String { "--:--" }
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
