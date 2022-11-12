import Foundation
import AVFoundation.AVPlayer
import Combine
import ModuleLinker

class SongIDConverter {
	func callAsFunction(_ id: String) -> URL {
		//TODO: REMOVE
		Bundle(for: AudioPlayerImpl.self).url(forResource: "getSchwifty", withExtension: "mp3")!
	}
}

//TODO: move to KMM
public class AudioPlayerImpl: AudioPlayer, ObservableObject {
	public static let shared = AudioPlayerImpl()
	
	private let songIDToURL = SongIDConverter()
	
	@Published public var songInfo: SongInfo?
	@Published public var playbackInfo: PlaybackInfo = PlaybackInfo() {
		didSet {
			guard let audioPlayer else { return }
			if playbackInfo.isPlaying {
				audioPlayer.play()
			} else {
				audioPlayer.pause()
			}
		}
	}
	
	private var audioPlayer: AVPlayer?
	
	private init() {}
	
	/// statedDuration is the value to show before the actual song file loads.  Once the song loads, we'll use that for duration.
	public func setSongId(_ songId: String) {
		let audioPlayer = AVPlayer(url: songIDToURL(songId))
		audioPlayer.addPeriodicTimeObserver(forInterval: CMTime(seconds: 1, preferredTimescale: 1), queue: DispatchQueue.main) { @MainActor [weak self] time in
			guard let self = self else { return }
			let songInfo = SongInfo(currentTime: Int(CMTimeGetSeconds(time)),
									totalTime: self.audioPlayer?.currentItem?.duration.isIndefinite == true ? 30 : Int(self.audioPlayer?.currentItem?.duration.seconds ?? 30),
									songID: songId)
			self.songInfo = songInfo
		}
		self.audioPlayer = audioPlayer
	}
	
	public func prev() {
		guard let audioPlayer else { fatalError("audio player not set up yet") }
		audioPlayer.seek(to: .zero)
	}
	
	public func next() {
		guard let audioPlayer else { fatalError("audio player not set up yet") }
		//TODO:
	}
}
