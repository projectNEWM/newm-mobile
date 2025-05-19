import Testing
@testable import AudioPlayer
import shared
import Resolver
import Mocks

@MainActor
struct VLCAudioPlayerTests {
	let player: VLCAudioPlayer
    
    init() async throws {
        AudioPlayerModule.shared.registerAllMockedServices(mockResolver: .mock)
        AudioPlayerModule.shared.registerAllServices()
        Resolver.root = .mock
        player = .init()
    }

	@Test
	func initialState() {
		#expect(player.state == .stopped)
		#expect(player.duration == nil)
		#expect(player.currentTime == nil)
		#expect(player.percentPlayed == nil)
		#expect(player.title == nil)
		#expect(player.artist == nil)
		#expect(player.artworkUrl == nil)
	}
	
	@Test
	func setTracks() {
        let tracks = NFTTrackMocksKt.mockTracks
		player.setTracks(Set(tracks), playFirstTrack: false)
		
		#expect(player.playQueueIsEmpty == false)
//        #expect(player.currentTrack?.title == "Lost In My Own Zone")
	}
	
	@Test
	func playPauseStop() async throws {
		let tracks = Set(NFTTrack.mocks)
		player.setTracks(tracks, playFirstTrack: true)
		
//		try await Task.sleep(for: .milliseconds(10000))
//		#expect(player.state == .playing)
//		
//		player.pause()
//		try await Task.sleep(for: .milliseconds(1000))
//		#expect(player.state == .paused)
		
		player.play()
		try await Task.sleep(for: .milliseconds(1000))
		#expect(player.state == .playing)
		
		player.stop()
		try await Task.sleep(for: .milliseconds(1000))
		#expect(player.state == .stopped)
	}
	
//	@Test
//	func nextPrevious() async throws {
//        let tracks = Set(NFTTrack.mocks)
//		player.setTracks(tracks)
//		try await Task.sleep(for: .milliseconds(300))
//		
//		player.play()
//		try await Task.sleep(for: .milliseconds(300))
//		
//		for sort in Sort.allCases {
//			print("--- Testing Sort: \(sort) ---")
//			
//			player.sort = sort
//			try await Task.sleep(for: .milliseconds(300))
//			
//			print("Current Track AFTER sort set: \(player.currentTrack?.title ?? "nil")")
//
//			switch sort {
//			case .artist(true):
//				#expect(player.currentTrack?.title == "Lost In My Own Zone", "Artist Asc Start")
//				player.next()
//				try await Task.sleep(for: .milliseconds(300))
//				#expect(player.currentTrack?.title == "Daisuke", "Artist Asc Next 1")
//				player.next()
//				try await Task.sleep(for: .milliseconds(300))
//				#expect(player.currentTrack?.title == "Dripdropz", "Artist Asc Next 2")
//			case .artist(false):
//				#expect(player.currentTrack?.title == "Love In The Water", "Artist Desc Start")
//				player.next()
//				try await Task.sleep(for: .milliseconds(300))
//				#expect(player.currentTrack?.title == "Bigger Dreams", "Artist Desc Next 1")
//				player.next()
//				try await Task.sleep(for: .milliseconds(300))
//				#expect(player.currentTrack?.title == "Sexiest Man Alive", "Artist Desc Next 2")
//			case .title(true):
//				#expect(player.currentTrack?.title == "Best Song Ever", "Title Asc Start")
//				player.next()
//				try await Task.sleep(for: .milliseconds(300))
//				#expect(player.currentTrack?.title == "Bigger Dreams", "Title Asc Next 1")
//				player.next()
//				try await Task.sleep(for: .milliseconds(300))
//				#expect(player.currentTrack?.title == "Daisuke", "Title Asc Next 2")
//			case .title(false):
//				#expect(player.currentTrack?.title == "Underdog, Pt. 2", "Title Desc Start")
//				player.next()
//				try await Task.sleep(for: .milliseconds(300))
//				#expect(player.currentTrack?.title == "Space Cowboy", "Title Desc Next 1")
//				player.next()
//				try await Task.sleep(for: .milliseconds(300))
//				#expect(player.currentTrack?.title == "Sexiest Man Alive", "Title Desc Next 2")
//			case .duration(true):
//				#expect(player.currentTrack?.title == "Sexiest Man Alive", "Duration Asc Start")
//				player.next()
//				try await Task.sleep(for: .milliseconds(300))
//				#expect(player.currentTrack?.title == "Love In The Water", "Duration Asc Next 1")
//				player.next()
//				try await Task.sleep(for: .milliseconds(300))
//				#expect(player.currentTrack?.title == "Lost In My Own Zone", "Duration Asc Next 2")
//			case .duration(false):
//				#expect(player.currentTrack?.title == "Best Song Ever", "Duration Desc Start")
//				player.next()
//				try await Task.sleep(for: .milliseconds(300))
//				#expect(player.currentTrack?.title == "Space Cowboy", "Duration Desc Next 1")
//				player.next()
//				try await Task.sleep(for: .milliseconds(300))
//				#expect(player.currentTrack?.title == "Bigger Dreams", "Duration Desc Next 2")
//			}
//		}
//	}
	
	@Test
	func seekToTrack() throws {
		let tracks = Set(NFTTrackMocksKt.mockTracks)
		player.setTracks(tracks)
		
		let trackToSeek = tracks.first { $0.title == "Daisuke" }!
		player.seek(toTrack: trackToSeek)
		
		#expect(player.currentTrack?.title == "Daisuke")
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
		
//		#expect(player.trackIsDownloaded(track) == false)
		
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
	func removeDownloadedSong() async {
		let track = NFTTrackMocksKt.mockTracks.first!

		await player.removeDownloadedSong(track)
		
		#expect(player.trackIsDownloaded(track) == false)
	}
}
