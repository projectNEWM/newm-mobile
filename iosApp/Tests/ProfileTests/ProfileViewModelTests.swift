import XCTest
import ModuleLinker
import Resolver
import shared
import Mocks
import Utilities
import Combine

@testable import Profile

let currentPassword = "currentPassword"

@MainActor
final class ProfileViewModelTests: XCTestCase {
	private var profileViewModel: ProfileViewModel!
	
	private var userDetailsUseCase: MockUserDetailsUseCase!
	private var connectWalletUseCase: MockConnectWalletUseCase!
	private var changePasswordUseCase: MockChangePasswordUseCase!
	private var errorLogger: MockErrorLogger!
	private var mockUser: User!
	
	override func setUp() async throws {
		try await super.setUp()
		
		Resolver.reset()
		Resolver.root = .mock
		MocksModule.shared.registerAllMockedServices()
		
		userDetailsUseCase = Resolver.resolve(UserDetailsUseCase.self) as! MockUserDetailsUseCase
		connectWalletUseCase = Resolver.resolve(ConnectWalletUseCase.self) as! MockConnectWalletUseCase
		changePasswordUseCase = Resolver.resolve(ChangePasswordUseCase.self) as! MockChangePasswordUseCase
		errorLogger = Resolver.resolve(ErrorReporting.self) as! MockErrorLogger
		mockUser = try! await userDetailsUseCase.fetchLoggedInUserDetails()
		
		profileViewModel = ProfileViewModel()
		let (cancellable, expectation) = loadingToastCountExpectation(expected: [true, false], vm: profileViewModel)
		await fulfillment(of: [expectation], timeout: 1)
		cancellable.cancel()
	}
	
	func testLoggedInUserDetails() async throws {
		await profileViewModel.loadUser()
		XCTAssertEqual(profileViewModel.fullName, "\(mockUser.firstName!) \(mockUser.lastName!)")
		XCTAssertEqual(profileViewModel.nickname, "@\(mockUser.nickname!)")
		XCTAssertEqual(profileViewModel.email, "\(mockUser.email!)")
		XCTAssertEqual(profileViewModel.bannerURL, URL(string: mockUser.bannerUrl!))
		XCTAssertEqual(profileViewModel.pictureURL, URL(string: mockUser.pictureUrl!))
		XCTAssertFalse(profileViewModel.enableSaveButon)
	}
	
	func loadingToastCountExpectation(expected: [Bool], vm: ProfileViewModel) -> (any Cancellable, XCTestExpectation) {
		var expected = expected
		let expectation = XCTestExpectation(description: "loadingDidUpdate")
		let cancellable = vm.$showLoadingToast
			.sink { value in
				XCTAssertEqual(expected.first, value)
				expected.removeFirst()
				if expected.isEmpty {
					expectation.fulfill()
				}
			}
		return (cancellable, expectation)
	}
	
	func testChangePassword() async throws {
		let (cancellable, expectation) = loadingToastCountExpectation(expected: [false, true, false], vm: profileViewModel)
		XCTAssertFalse(profileViewModel.enableSaveButon)
		XCTAssertFalse(profileViewModel.showSaveButton)
		profileViewModel.newPassword = "newPassword"
		XCTAssertFalse(profileViewModel.enableSaveButon)
		XCTAssertTrue(profileViewModel.showSaveButton)
		profileViewModel.confirmPassword = profileViewModel.newPassword
		XCTAssertFalse(profileViewModel.enableSaveButon)
		XCTAssertTrue(profileViewModel.showSaveButton)
		profileViewModel.currentPassword = currentPassword
		XCTAssertTrue(profileViewModel.enableSaveButon)
		XCTAssertTrue(profileViewModel.showSaveButton)
		
		await profileViewModel.save()
		XCTAssertTrue(changePasswordUseCase.changePasswordCalled)
		await fulfillment(of: [expectation], timeout: 0.1)
		XCTAssertFalse(profileViewModel.enableSaveButon)
		XCTAssertFalse(profileViewModel.showSaveButton)
		cancellable.cancel()
	}
	
	func testWalletConnection() async throws {
		XCTAssertFalse(profileViewModel.isWalletConnected)
		
		try await connectWalletUseCase.connect(walletConnectionId: "newm234324234234243")
		NotificationCenter.default.post(name: NSNotification.Name(Notification().walletConnectionStateChanged), object: nil)
		
		try await Task.sleep(nanoseconds: 100_000_000) // 0.1 seconds
		
		XCTAssertTrue(profileViewModel.isWalletConnected)
		profileViewModel.disconnectWallet()
		NotificationCenter.default.post(name: NSNotification.Name(Notification().walletConnectionStateChanged), object: nil)
		
		try await Task.sleep(nanoseconds: 100_000_000) // 0.1 seconds
		
		XCTAssertFalse(profileViewModel.isWalletConnected)
	}

	func testLoadUserErrors() async throws {
		let error = "test error".newmError
		userDetailsUseCase.errorToThrow = error
		await profileViewModel.loadUser()
		XCTAssertEqual(profileViewModel.errorAlert, error.errorDescription)
		XCTAssertEqual(errorLogger.errorsLogged.first?.localizedDescription, error.localizedDescription)
	}
	
	func testSaveErrors() async throws {
		let error = "test error".newmError
		changePasswordUseCase.errorToThrow = error
		profileViewModel.currentPassword = currentPassword
		profileViewModel.newPassword = "new"
		profileViewModel.confirmPassword = profileViewModel.newPassword
		await profileViewModel.save()
		XCTAssertEqual(profileViewModel.errorAlert, error.errorDescription)
		XCTAssertEqual(errorLogger.errorsLogged.first!.localizedDescription, error.localizedDescription)
	}
}
