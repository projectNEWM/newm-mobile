import Foundation
import AVFoundation.AVPlayer
import Combine

class SongIDConverter {
	func callAsFunction(_ id: String) -> URL {
		//TODO: REMOVE
		Bundle(for: SongPlayingModule.self).url(forResource: "getSchwifty", withExtension: "mp3")!
	}
}

//TODO: move to KMM
class AudioPlayerImpl: AudioPlayer, ObservableObject {
	
	static let shared = AudioPlayerImpl()
	
	private let songIDToURL = SongIDConverter()
	
	@Published var isPlaying: Bool = false {
		didSet {
			guard let audioPlayer else { fatalError("audio player not set up yet") }
			if isPlaying {
				audioPlayer.play()
			} else {
				audioPlayer.pause()
			}
		}
	}
	
	@Published var songInfo: SongInfo?
	@Published var playbackInfo: PlaybackInfo?

	private var audioPlayer: AVPlayer?
	
	private init() {}
	
	private func reset() {
		
	}
	
	/// statedDuration is the value to show before the actual song file loads.  Once the song loads, we'll use that for duration.
	func setSongId(_ songId: String, statedDuration: Int) {
		audioPlayer?.pause()
		let audioPlayer = AVPlayer(url: songIDToURL(songId))
		audioPlayer.addPeriodicTimeObserver(forInterval: CMTime(seconds: 1, preferredTimescale: 1), queue: DispatchQueue.main) { @MainActor [weak self] time in
			guard let self = self else { return }
			let songInfo = SongInfo(currentTime: Int(CMTimeGetSeconds(time)),
									 totalTime: audioPlayer.currentItem?.duration.isIndefinite == true ? statedDuration : Int(audioPlayer.currentItem?.duration.seconds ?? 0),
									 songID: songId)
			self.songInfo = songInfo
		}
		self.audioPlayer = audioPlayer
	}
	
	func prev() {
		guard let audioPlayer else { fatalError("audio player not set up yet") }
		audioPlayer.seek(to: .zero)
	}
	
	func next() {
		guard let audioPlayer else { fatalError("audio player not set up yet") }
		//TODO:
	}
}
