import Foundation
import AVFoundation.AVPlayer
import Combine

//TODO: move to KMM
class AudioPlayerImpl: AudioPlayer, ObservableObject {
	static let shared = AudioPlayerImpl()

	@Published var playbackState: PlaybackState = .stopped {
		didSet {
			switch playbackState {
			case .playing:
				audioPlayer.play()
			case .paused:
				audioPlayer.pause()
			case .stopped:
				audioPlayer.pause()
				audioPlayer.seek(to: .zero)
			}
		}
	}
	
	@Published var repeatMode: RepeatMode? = nil
	@Published var shuffle: Bool = false
	
	@Published var currentTime: Int = 0
	@Published var totalTime: Int = 0
		
	private var audioPlayer = AVPlayer(url: AudioPlayerImpl.songURL)
	//TODO: remove this
	static private let songURL = Bundle(for: SongPlayingModule.self).url(forResource: "getSchwifty", withExtension: "mp3")!
	
	private init() {}
	
	func setSongId(_ songId: String) {
		audioPlayer.pause()
		audioPlayer = AVPlayer(url: AudioPlayerImpl.songURL)
		audioPlayer.addPeriodicTimeObserver(forInterval: CMTime(seconds: 1, preferredTimescale: 1), queue: DispatchQueue.global(qos: .utility)) { [weak self] time in
			self?.currentTime = Int(CMTimeGetSeconds(time))
			self?.totalTime = self?.audioPlayer.currentItem?.duration.isIndefinite == true ? 0 : Int(self?.audioPlayer.currentItem?.duration.seconds ?? 0)
			
			print("\(self!.currentTime)\t\(self!.totalTime)")
		}
	}
}
