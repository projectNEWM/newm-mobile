import XCTest

@testable import Library
import shared

final class LibraryFilterTests: XCTestCase {
	func testDurationFilter() {
		XCTAssertTrue(
			NFTTrack(id: "1", policyId: "1", title: "", assetName: "", amount: 0, imageUrl: "", audioUrl: "", duration: 40, artists: [], genres: [], moods: [], isStreamToken: false, isDownloaded: false)
				.isAboveDurationFilter(30)
		)

		XCTAssertFalse(
			NFTTrack(id: "1", policyId: "1", title: "", assetName: "", amount: 0, imageUrl: "", audioUrl: "", duration: 40, artists: [], genres: [], moods: [], isStreamToken: false, isDownloaded: false)
				.isAboveDurationFilter(50)
		)
		
		XCTAssertFalse(
			NFTTrack(id: "1", policyId: "1", title: "", assetName: "", amount: 0, imageUrl: "", audioUrl: "", duration: 30, artists: [], genres: [], moods: [], isStreamToken: false, isDownloaded: false)
				.isAboveDurationFilter(30)
		)

		XCTAssertFalse(
			NFTTrack(id: "1", policyId: "1", title: "", assetName: "", amount: 0, imageUrl: "", audioUrl: "", duration: -1, artists: [], genres: [], moods: [], isStreamToken: false, isDownloaded: false)
				.isAboveDurationFilter(0)
		)
	}
	
	func testContainsSearchText() {
		let track = NFTTrack(id: "1", policyId: "1", title: "title", assetName: "", amount: 0, imageUrl: "", audioUrl: "", duration: 30, artists: ["Test"], genres: [], moods: [], isStreamToken: false, isDownloaded: false)
		XCTAssertTrue(track.containsSearchText("T"))
		XCTAssertTrue(track.containsSearchText("Te"))
		XCTAssertTrue(track.containsSearchText("Test"))
		XCTAssertTrue(track.containsSearchText("s"))
		XCTAssertTrue(track.containsSearchText("es"))
		XCTAssertTrue(track.containsSearchText("ti"))
		XCTAssertTrue(track.containsSearchText("l"))
		XCTAssertTrue(track.containsSearchText("t"))
		XCTAssertTrue(track.containsSearchText(""))
	}
}
