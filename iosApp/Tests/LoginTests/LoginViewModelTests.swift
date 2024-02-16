import XCTest
@testable import Login
import Resolver
import shared
import Mocks

@MainActor
final class LoginViewModelTests: XCTestCase {
	override func setUp() {
		super.setUp()
		LoginModule.shared.registerAllServices()
		LoginModule.shared.registerAllMockedServices(mockResolver: .mock)
		
		Resolver.root = .mock
		
		KoinKt.doInitKoin(enableNetworkLogs: true)
	}
	
	func testLogin() async throws {
		let vm = LandingViewModel()
		vm.email = "mulrich@newm.io"
		vm.password = "Password1"
		await vm.login()
		XCTAssertTrue(Resolver.resolve(UserSessionUseCase.self).isLoggedIn())
	}
}
