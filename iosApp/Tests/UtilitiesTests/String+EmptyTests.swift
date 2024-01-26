import XCTest
@testable import Utilities

final class String_NilIfEmptyTests: XCTestCase {
	func testNilIfEmpty() {
		XCTAssertNil("".nilIfEmpty)
		XCTAssertNotNil("asdf".nilIfEmpty)
		XCTAssertTrue((nil as String?).emptyIfNil.isEmpty)
		XCTAssertFalse(("asdf" as String?).emptyIfNil.isEmpty)
	}
}
