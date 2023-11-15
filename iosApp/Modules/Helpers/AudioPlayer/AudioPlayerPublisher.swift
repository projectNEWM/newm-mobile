import Foundation

public class AudioPlayerPublisher: AudioPlayerDelegate, ObservableObject {
	@Published public var state: AudioPlayerState?
	@Published public var duration: TimeInterval?
	@Published public var loadedTimeRange: TimeRange?
	@Published public var metadata: Metadata?
	@Published public var currentTime: TimeInterval?
	@Published public var percentPlayed: Float?
	
	public func audioPlayer(_ audioPlayer: AudioPlayer, didChangeStateFrom from: AudioPlayerState, to state: AudioPlayerState) {
		self.state = state
	}
	
	public func audioPlayer(_ audioPlayer: AudioPlayer, didFindDuration duration: TimeInterval, for item: AudioItem) {
		self.duration = duration
	}
	
	public func audioPlayer(_ audioPlayer: AudioPlayer, didLoad range: TimeRange, for item: AudioItem) {
		loadedTimeRange = range
	}
	
	public func audioPlayer(_ audioPlayer: AudioPlayer, didUpdateEmptyMetadataOn item: AudioItem, withData data: Metadata) {
		self.metadata = data
	}
	
	public func audioPlayer(_ audioPlayer: AudioPlayer, didUpdateProgressionTo time: TimeInterval, percentageRead: Float) {
		percentPlayed = percentageRead
	}
}
