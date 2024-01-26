import XCTest
@testable import Utilities

final class ErrorSetTests: XCTestCase {
	var error1 = NEWMError(errorDescription: "Error1")
	var error2 = NEWMError(errorDescription: "Error2")

	func testErrorSet() {
		var errorSet = ErrorSet()
		XCTAssertFalse(errorSet.hasError)
		XCTAssertNil(errorSet.currentError)
		errorSet.append(error1)
		XCTAssertTrue(errorSet.hasError)
		XCTAssertNotNil(errorSet.currentError)
		errorSet.popFirstError()
		XCTAssertFalse(errorSet.hasError)
		XCTAssertNil(errorSet.currentError)
		errorSet.append(error1)
		errorSet.append(error2)
		XCTAssertTrue(errorSet.hasError)
		XCTAssertEqual(errorSet.currentError, error1)
		errorSet.popFirstError()
		XCTAssertEqual(errorSet.currentError, error2)
	}
}

extension NEWMError: Equatable {
	public static func == (lhs: NEWMError, rhs: NEWMError) -> Bool {
		lhs.errorDescription == rhs.errorDescription
	}
}
