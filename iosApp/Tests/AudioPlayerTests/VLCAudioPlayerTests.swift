import Testing
@testable import AudioPlayer
import shared
import Resolver

@MainActor
struct VLCAudioPlayerTests {
	let player: VLCAudioPlayer
//	
//	func setup() {
//		Resolver.reset()
//		player.stop()
//	}
	
	@Test
	func initialState() {
		#expect(player.state == .stopped)
		#expect(player.duration == nil)
		#expect(player.currentTime == nil)
		#expect(player.percentPlayed == nil)
		#expect(player.title == nil)
		#expect(player.artist == nil)
		#expect(player.artworkUrl == nil)
		#expect(player.willPlay == false)
	}
	
	@Test
	func setTracks() {
		let tracks = NFTTrackMocksKt.mockTracks
		player.setTracks(Set(tracks), playFirstTrack: false)
		
		#expect(player.playQueueIsEmpty == false)
		#expect(player.currentTrack == tracks.first)
	}
	
	@Test
	func playPauseStop() async throws {
		let tracks = NFTTrackMocksKt.mockTracks
		player.setTracks(Set(tracks))
		
		player.play()
		#expect(player.state == .playing)
		
		player.pause()
		#expect(player.state == .paused)
		
		player.stop()
		#expect(player.state == .stopped)
	}
	
	@Test
	func nextPrevious() {
		let tracks = Set(NFTTrackMocksKt.mockTracks)
		player.setTracks(tracks)
		
		#expect(player.currentTrack?.title == "Track 1")
		
		player.next()
		#expect(player.currentTrack?.title == "Track 2")
		
		player.next()
		#expect(player.currentTrack?.title == "Track 3")
		
		player.prev()
		#expect(player.currentTrack?.title == "Track 2")
	}
	
	@Test
	func seekToTrack() throws {
		let tracks = Set(NFTTrackMocksKt.mockTracks)
		player.setTracks(tracks)
		
		let trackToSeek = tracks.first { $0.title == "Track 2" }!
		player.seek(toTrack: trackToSeek)
		
		#expect(player.currentTrack?.title == "Track 2")
	}
	
	@Test
	func repeatMode() {
		#expect(player.repeatMode == .none)
		
		player.cycleRepeatMode()
		#expect(player.repeatMode == .all)
		
		player.cycleRepeatMode()
		#expect(player.repeatMode == .one)
		
		player.cycleRepeatMode()
		#expect(player.repeatMode == .none)
	}
	
	@Test
	func shuffleMode() {
		#expect(player.shuffle == false)
		
		player.shuffle = true
		#expect(player.shuffle == true)
		
		player.shuffle = false
		#expect(player.shuffle == false)
	}
	
	@Test
	func downloadTrack() async throws {
		let track = NFTTrackMocksKt.mockTracks.first!
		
		#expect(player.trackIsDownloaded(track) == false)
		
		try await player.downloadTrack(track)
		
		#expect(player.trackIsDownloaded(track) == true)
	}
	
	@Test
	func cancelDownload() {
		let track = NFTTrackMocksKt.mockTracks.first!

		player.cancelDownload(track)
		
		#expect(player.loadingProgress[track] == nil)
	}
	
	@Test
	func removeDownloadedSong() {
		let track = NFTTrackMocksKt.mockTracks.first!

		player.removeDownloadedSong(track)
		
		#expect(player.trackIsDownloaded(track) == false)
	}
}
