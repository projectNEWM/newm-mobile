import Foundation
import AVFoundation.AVPlayer
import Combine
import ModuleLinker
import shared

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
	
	@Published public var song: Song? {
		didSet {
			guard let song else {
				audioPlayer?.pause()
				audioPlayer = nil
				return
			}
			guard song != oldValue else { return }
			let audioPlayer = AVPlayer(url: songIDToURL(song.songId))
			audioPlayer.addPeriodicTimeObserver(forInterval: CMTime(seconds: 1, preferredTimescale: 1), queue: DispatchQueue.main) { @MainActor [weak self] time in
				guard let self = self else { return }
				self.playbackInfo.currentTime = Int(CMTimeGetSeconds(time))
				self.playbackInfo.totalTime = self.audioPlayer?.currentItem?.duration.isIndefinite == true ? 30 : Int(self.audioPlayer?.currentItem?.duration.seconds ?? 30)
			}
			self.audioPlayer = audioPlayer
		}
	}
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
		
	public func prev() {
		guard let audioPlayer else { fatalError("audio player not set up yet") }
		audioPlayer.seek(to: .zero)
	}
	
	public func next() {
		guard let audioPlayer else { fatalError("audio player not set up yet") }
		//TODO:
	}
}