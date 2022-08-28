import XCTest
import Utilities

enum TestEnum: Codable {
	case case1
	case case2
}

struct TestStruct: Codable, Equatable {
	var testString: String
	var testInt: Int
	var testEnum: TestEnum
	var testFloat: Float
	var testCGFlost: CGFloat
	
	init() {
		testString = "string"
		testInt = 1
		testEnum = .case1
		testFloat = 1
		testCGFlost = 1
	}
}

class UserDefaultsPersistenceTest: XCTestCase {
	private static let userDefaultsSuiteName = "unitTest.userDefaultsPersistence"
	private let userDefaults = UserDefaults(suiteName: userDefaultsSuiteName)!
	
	override func setUpWithError() throws {
		userDefaults.removePersistentDomain(forName: Self.userDefaultsSuiteName)
	}
	
	func testIntUserDefault() throws {
		@UserDefault("test_int", defaultValue: 5, userDefaults: userDefaults) var testInt: Int
		XCTAssert(testInt == 5)
		testInt = 6
		XCTAssert(testInt == 6)
	}
	
	func testStringUserDefault() {
		@UserDefault("test_string", defaultValue: "string", userDefaults: userDefaults) var testString: String
		XCTAssert(testString == "string")
		testString = "string2"
		XCTAssert(testString == "string2")
	}
	
	func testEnumUserDefault() {
		@UserDefault("test_enum", defaultValue: TestEnum.case1, userDefaults: userDefaults) var testEnum: TestEnum
		XCTAssert(testEnum == .case1)
		testEnum = .case2
		XCTAssert(testEnum == .case2)
	}
	
	func testStructUserDefault() {
		@UserDefault("test_struct", defaultValue: TestStruct(), userDefaults: userDefaults) var testStruct: TestStruct
		XCTAssert(testStruct == TestStruct())
		testStruct.testString = "string2"
		testStruct.testInt = 2
		testStruct.testEnum = .case2
		testStruct.testFloat = 2
		testStruct.testCGFlost = 2
		XCTAssert(testStruct.testString == "string2")
		XCTAssert(testStruct.testInt == 2)
		XCTAssert(testStruct.testEnum == .case2)
		XCTAssert(testStruct.testFloat == 2)
		XCTAssert(testStruct.testCGFlost == 2)
		XCTAssert(testStruct != TestStruct())
	}
}

extension String: RawRepresentable {
	public var rawValue: String { self }
	public init(rawValue: String) { self = rawValue }
}
