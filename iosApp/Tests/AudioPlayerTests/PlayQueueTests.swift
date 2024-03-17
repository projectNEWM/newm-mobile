import XCTest
@testable import AudioPlayer
import shared
import Resolver

final class PlayQueueTests: XCTestCase {
	var playQueue: PlayQueue!
	var tracks: [NFTTrack]!
	
	override class func setUp() {
		super.setUp()
		AudioPlayerModule.shared.registerAllMockedServices(mockResolver: .mock)
		AudioPlayerModule.shared.registerAllServices()
		Resolver.root = .mock
	}
	
	override func setUp() {
		super.setUp()
		if let bundleID = Bundle.main.bundleIdentifier {
			UserDefaults.standard.removePersistentDomain(forName: bundleID)
		}
		let tracks = [
			NFTTrack(id: "0", policyId: "B", title: "i", assetName: "Asset2", amount: 1, imageUrl: "", audioUrl: "", duration: 200, artists: ["Artist 2"], genres: ["Rock"], moods: ["Sad"], isDownloaded: true),
			NFTTrack(id: "1", policyId: "A", title: "f", assetName: "Asset1", amount: 1, imageUrl: "", audioUrl: "", duration: 400, artists: ["Artist 1"], genres: ["Pop"], moods: ["Happy"], isDownloaded: true),
			NFTTrack(id: "2", policyId: "C", title: "h", assetName: "Asset3", amount: 1, imageUrl: "", audioUrl: "", duration: 300, artists: ["Artist 3"], genres: ["Jazz"], moods: ["Relaxed"], isDownloaded: true),
			NFTTrack(id: "3", policyId: "r", title: "g", assetName: "Asset4", amount: 1, imageUrl: "", audioUrl: "", duration: 500, artists: ["Artist 4"], genres: ["Jazz"], moods: ["Relaxed"], isDownloaded: true),
			NFTTrack(id: "4", policyId: "z", title: "e", assetName: "Asset7", amount: 1, imageUrl: "", audioUrl: "", duration: 44, artists: ["Artist 7"], genres: ["Jazz"], moods: ["Relaxed"], isDownloaded: true),
			NFTTrack(id: "5", policyId: "e", title: "z", assetName: "Asset5", amount: 1, imageUrl: "", audioUrl: "", duration: 30, artists: ["Artist 5"], genres: ["Jazz"], moods: ["Relaxed"], isDownloaded: true),
			NFTTrack(id: "6", policyId: "d", title: "r", assetName: "Asset6", amount: 1, imageUrl: "", audioUrl: "", duration: 44, artists: ["Artist 6"], genres: ["Jazz"], moods: ["Relaxed"], isDownloaded: true),
		]
		var playQueue = PlayQueue()
		playQueue.originalTracks = Set(tracks)
		self.tracks = tracks
		self.playQueue = playQueue
	}
	
	override func tearDown() {
		playQueue = nil
		tracks = nil
		super.tearDown()
	}
	
	func testSeekingSorted() throws {
		//1, 0, 2, 3, 5, 6, 4
		playQueue.sortCriteria = .artist(ascending: true)
		
		XCTAssertNil(try playQueue.currentTrack())
		try playQueue.seekToFirst()
		XCTAssertEqual(try playQueue.currentTrack(), tracks[1])
		XCTAssertEqual(playQueue.nextTrack(), tracks[0])
		XCTAssertEqual(playQueue.previousTrack(), tracks[1])
		XCTAssertEqual(try playQueue.currentTrack(), tracks[1])
		var lastTrack: NFTTrack?
		while let nextTrack = playQueue.nextTrack() {
			lastTrack = nextTrack
		}
		XCTAssertEqual(lastTrack, tracks[4])
		XCTAssertNil(playQueue.nextTrack())
		XCTAssertNil(try playQueue.currentTrack())
		try playQueue.seekToFirst()
		XCTAssertEqual(try playQueue.currentTrack(), tracks[1])
		
		//4, 6, 5, 3, 2, 0, 1
		playQueue.sortCriteria = .artist(ascending: false)
		
		XCTAssertEqual(try playQueue.currentTrack(), tracks[1])
		XCTAssertEqual(playQueue.nextTrack(), nil)
		try playQueue.seekToFirst()
		XCTAssertEqual(try playQueue.currentTrack(), tracks[4])
		XCTAssertEqual(playQueue.nextTrack(), tracks[6])
		XCTAssertEqual(playQueue.nextTrack(), tracks[5])
		XCTAssertEqual(playQueue.previousTrack(), tracks[6])
		try playQueue.seekToFirst()
		XCTAssertEqual(try playQueue.currentTrack(), tracks[4])
		
		//id==7
		var prevTrack = try playQueue.currentTrack()
		//4, 1, 3, 2, 0, 6, 5
		playQueue.sortCriteria = .title(ascending: true)
		
		XCTAssertEqual(try playQueue.currentTrack(), prevTrack)
		XCTAssertEqual(playQueue.nextTrack(), tracks[1])
		XCTAssertEqual(playQueue.nextTrack(), tracks[3])
		XCTAssertEqual(playQueue.previousTrack(), tracks[1])
		XCTAssertEqual(playQueue.previousTrack(), tracks[4])
		XCTAssertEqual(playQueue.previousTrack(), tracks[4])
		XCTAssertEqual(try playQueue.currentTrack(), tracks[4])
		
		//4
		prevTrack = try playQueue.currentTrack()
		//5, 6, 0, 2, 3, 1, 4
		playQueue.sortCriteria = .title(ascending: false)
		
		XCTAssertEqual(try playQueue.currentTrack(), prevTrack)
		XCTAssertNil(playQueue.nextTrack())
		XCTAssertNil(try playQueue.currentTrack())
		XCTAssertNil(playQueue.previousTrack())
		try playQueue.seekToFirst()
		XCTAssertEqual(try playQueue.currentTrack(), tracks[5])
		[6, 0, 2, 3, 1, 4].forEach {
			XCTAssertEqual(playQueue.nextTrack(), tracks[$0])
		}
		XCTAssertEqual(try playQueue.currentTrack(), tracks[4])
		[1, 3, 2, 0, 6].forEach {
			XCTAssertEqual(playQueue.previousTrack(), tracks[$0])
		}
		
		//6
		prevTrack = try playQueue.currentTrack()
		//5, 6, 4, 0, 2, 1, 3
		playQueue.sortCriteria = .duration(ascending: true)
		
		XCTAssertEqual(try playQueue.currentTrack(), prevTrack)
		XCTAssertEqual(playQueue.nextTrack(), tracks[4])
		XCTAssertEqual(playQueue.previousTrack(), tracks[6])
		try playQueue.seekToFirst()
		XCTAssertEqual(try playQueue.currentTrack(), tracks[5])
		[6, 4, 0, 2, 1, 3].forEach {
			XCTAssertEqual(playQueue.nextTrack(), tracks[$0])
		}
		XCTAssertEqual(try playQueue.currentTrack(), tracks[3])
		[1, 2, 0, 4, 6, 5].forEach {
			XCTAssertEqual(playQueue.previousTrack(), tracks[$0])
		}
		
		//5
		prevTrack = try playQueue.currentTrack()
		//3, 1, 2, 0, 6, 4, 5
		playQueue.sortCriteria = .duration(ascending: false)
		XCTAssertEqual(try playQueue.currentTrack(), prevTrack)
		XCTAssertEqual(playQueue.previousTrack(), tracks[4])
		XCTAssertEqual(playQueue.nextTrack(), tracks[5])
		XCTAssertNil(playQueue.nextTrack())
		try playQueue.seekToFirst()
		XCTAssertEqual(try playQueue.currentTrack(), tracks[3])
		[1, 2, 0, 6, 4, 5].forEach {
			XCTAssertEqual(playQueue.nextTrack(), tracks[$0])
		}
		[4, 6, 0, 2, 1, 3].forEach {
			XCTAssertEqual(playQueue.previousTrack(), tracks[$0])
		}
		XCTAssertEqual(playQueue.previousTrack(), tracks[3])
	}
	
	func testOriginalOrderPreservedWhenTurningShuffleOff() throws {
		try playQueue.seekToFirst()
		let track1 = try playQueue.currentTrack()
		let track2 = playQueue.nextTrack()
		_ = playQueue.previousTrack()
		playQueue.shuffle = true
		XCTAssertEqual(track1, try playQueue.currentTrack())
		_ = playQueue.nextTrack()
		_ = playQueue.previousTrack()
		playQueue.shuffle = false
		XCTAssertEqual(track1, try playQueue.currentTrack())
		XCTAssertEqual(track2, playQueue.nextTrack())
		
		playQueue.shuffle = true
		XCTAssertEqual(try playQueue.currentTrack(), playQueue.previousTrack())
	}
	
	func testRepeatModes_endOfTrack() throws {
		let tracks = tracks.sorted(by: playQueue.sortCriteria.comparator)
		try playQueue.seekToFirst()
		playQueue.repeatMode = .one
		let firstTrack = try playQueue.currentTrack()
		XCTAssertEqual(try playQueue.currentTrack(), firstTrack)
		XCTAssertEqual(playQueue.nextTrack(), firstTrack)
		
		let currentTrack = try playQueue.currentTrack()
		XCTAssertEqual(currentTrack, firstTrack)
		playQueue.repeatMode = .all
		while playQueue.nextTrack() != tracks.last! { }
		XCTAssertEqual(try playQueue.currentTrack(), tracks.last)
		XCTAssertEqual(playQueue.nextTrack()!, firstTrack!, "Should wrap to the first track in .all mode")
		
		playQueue.repeatMode = .none
		_ = (1...tracks.count).map { _ in playQueue.nextTrack() }
		XCTAssertNil(playQueue.nextTrack(), "Should return nil when no more tracks in .none mode")
	}
	
	func testRepeatModes_userInitiated() throws {
		let tracks = tracks.sorted(by: playQueue.sortCriteria.comparator)
		try playQueue.seekToFirst()
		playQueue.repeatMode = .one
		XCTAssertEqual(playQueue.nextTrack(userInitiated: true), tracks[1])
		
		try playQueue.seekToFirst()
		playQueue.repeatMode = .all
		while playQueue.nextTrack() != tracks.last! { }
		XCTAssertEqual(playQueue.nextTrack(userInitiated: true), tracks.first, "Should wrap to the first track in .all mode")
		
		playQueue.repeatMode = .none
		while playQueue.nextTrack() != tracks.last! { }
		XCTAssertNil(playQueue.nextTrack(userInitiated: true), "Should return nil when no more tracks in .none mode")
		
		playQueue.repeatMode = .all
		try playQueue.seekToFirst()
		XCTAssertEqual(playQueue.previousTrack(), tracks.last)
		
		playQueue.repeatMode = .one
		try playQueue.seekToFirst()
		XCTAssertEqual(playQueue.previousTrack(), tracks.first)
		
		playQueue.repeatMode = .none
		try playQueue.seekToFirst()
		XCTAssertEqual(playQueue.previousTrack(), tracks.first)
	}
	
	func testDurationFilter() {
		playQueue.durationFilter = 250
		while let track = playQueue.nextTrack() {
			XCTAssertLessThan(track.duration, 250, "Track shorter than 250 should be filtered out")
		}
		
		playQueue.durationFilter = 350
		while let track = playQueue.nextTrack() {
			XCTAssertLessThan(track.duration, 350, "Track shorter than 350 should be filtered out")
		}
	}
	
	func testTextFilter() throws {
		playQueue.textFilter = "Artist 2"
		try playQueue.seekToFirst()
		XCTAssertEqual(try playQueue.currentTrack(), tracks[0], "Only 'Artist 2' should be in the queue")
		
		playQueue.textFilter = "Artist 3"
		try playQueue.seekToFirst()
		XCTAssertEqual(try playQueue.currentTrack(), tracks[2], "Only 'Artist 3' should be in the queue")
	}
	
	func testMultipleFilters() throws {
		playQueue.textFilter = "f"
		playQueue.durationFilter = 250
		try playQueue.seekToFirst()
		XCTAssertEqual(try? playQueue.currentTrack(), tracks[1], "Only 'Song A' should pass the filters")
		XCTAssertNil(playQueue.nextTrack())
	}
	
	func testSeekToTrack() throws {
		try playQueue.seekToTrack(tracks[4])
		XCTAssertEqual(try playQueue.currentTrack(), tracks[4])
		XCTAssertThrowsError(try playQueue.seekToTrack(NFTTrack(id: "999", policyId: "999", title: "hi", assetName: "hi", amount: 4, imageUrl: "", audioUrl: "", duration: 9, artists: [], genres: [], moods: [], isDownloaded: false)))
	}
	
	func testCycleRepeatMode() {
		XCTAssertEqual(playQueue.repeatMode, .none)
		playQueue.cycleRepeatMode()
		XCTAssertEqual(playQueue.repeatMode, .all)
		playQueue.cycleRepeatMode()
		XCTAssertEqual(playQueue.repeatMode, .one)
		playQueue.cycleRepeatMode()
		XCTAssertEqual(playQueue.repeatMode, .none)
	}
	
	func testCycleSort() {
		XCTAssertEqual(playQueue.sortCriteria, .artist(ascending: true))
		playQueue.cycleTitleSort()
		XCTAssertEqual(playQueue.sortCriteria, .title(ascending: true))
		playQueue.cycleTitleSort()
		XCTAssertEqual(playQueue.sortCriteria, .title(ascending: false))
		playQueue.cycleTitleSort()
		XCTAssertEqual(playQueue.sortCriteria, .title(ascending: true))
		playQueue.cycleArtistSort()
		XCTAssertEqual(playQueue.sortCriteria, .artist(ascending: true))
		playQueue.cycleArtistSort()
		XCTAssertEqual(playQueue.sortCriteria, .artist(ascending: false))
		playQueue.cycleArtistSort()
		XCTAssertEqual(playQueue.sortCriteria, .artist(ascending: true))
		playQueue.cycleDurationSort()
		XCTAssertEqual(playQueue.sortCriteria, .duration(ascending: true))
		playQueue.cycleDurationSort()
		XCTAssertEqual(playQueue.sortCriteria, .duration(ascending: false))
		playQueue.cycleDurationSort()
		XCTAssertEqual(playQueue.sortCriteria, .duration(ascending: true))
	}
	
	func testSeekToEndAndRepeat() throws {
		let tracks = tracks.sorted(by: playQueue.sortCriteria.comparator)
		try playQueue.seekToFirst()
		while playQueue.nextTrack() != tracks.last! { }
		playQueue.repeatMode = .all
		XCTAssertEqual(playQueue.nextTrack(), tracks[0])
	}
	
	func testHasNextTrack() throws {
		let tracks = tracks.sorted(by: playQueue.sortCriteria.comparator)
		func checkHasNext() {
			XCTAssertTrue(playQueue.hasNextTrack)
		}
		func checkDoesntHaveNext() {
			XCTAssertFalse(playQueue.hasNextTrack)
		}
		checkDoesntHaveNext()
		try playQueue.seekToFirst()
		checkHasNext()
		try playQueue.seekToTrack(tracks.last!)
		checkDoesntHaveNext()
		playQueue.repeatMode = .all
		checkHasNext()
		playQueue.repeatMode = .one
		checkHasNext()
		try playQueue.seekToTrack(tracks[4])
		checkHasNext()
		playQueue.repeatMode = .all
		checkHasNext()
		playQueue.repeatMode = .none
		checkHasNext()
	}
	
	func testHasPrevTrack() throws {
		let tracks = tracks.sorted(by: playQueue.sortCriteria.comparator)
		func checkHasPrev() {
			XCTAssertTrue(playQueue.hasPrevTrack)
		}
		func checkDoesntHavePrev() {
			XCTAssertFalse(playQueue.hasPrevTrack)
		}
		checkDoesntHavePrev()
		try playQueue.seekToFirst()
		checkHasPrev()
		try playQueue.seekToTrack(tracks.last!)
		checkHasPrev()
		playQueue.repeatMode = .all
		checkHasPrev()
		playQueue.repeatMode = .one
		checkHasPrev()
		try playQueue.seekToTrack(tracks[4])
		checkHasPrev()
		playQueue.repeatMode = .all
		checkHasPrev()
		playQueue.repeatMode = .none
		checkHasPrev()
		try playQueue.seekToFirst()
		checkHasPrev()
		playQueue.repeatMode = .all
		checkHasPrev()
		playQueue.repeatMode = .one
		checkHasPrev()
	}
}

