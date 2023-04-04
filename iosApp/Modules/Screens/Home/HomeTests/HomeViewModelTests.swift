import XCTest
@testable import Home

class HomeViewModelTests: XCTestCase {
	func testHomeViewModel() {
		let vm = HomeViewModel()
		XCTAssertNil(vm.route)
		vm.artistTapped(id: "1")
		guard case .artist = vm.route else { return XCTFail() }
		vm.route = nil
		vm.songTapped(id: "1")
		guard case .nowPlaying = vm.route else { return XCTFail() }
	}
}
