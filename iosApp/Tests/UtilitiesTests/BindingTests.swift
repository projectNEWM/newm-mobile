import XCTest
import SwiftUI
@testable import Utilities

final class BindingTests: XCTestCase {
	@ObservedObject var bindingTestClass = BindingTestClass()

	func testBinding() {
		XCTAssertFalse(isPresent($bindingTestClass.testBinding).wrappedValue)
		bindingTestClass.testBinding = Int.random(in: 1..<100)
		XCTAssertTrue(isPresent($bindingTestClass.testBinding).wrappedValue)
		bindingTestClass.testBinding = nil
		XCTAssertFalse(isPresent($bindingTestClass.testBinding).wrappedValue)
	}
}

class BindingTestClass: ObservableObject {
	@Published var testBinding: Int?
}
