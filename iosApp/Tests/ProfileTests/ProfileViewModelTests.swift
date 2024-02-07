import XCTest
import ModuleLinker
import Resolver
import shared
import Mocks
import Utilities
import Combine

@testable import Profile

let mockUser = UserMocksKt.mockUsers.first!
let currentPassword = "currentPassword"
let fetchLatency: Double = 0.1

@MainActor
final class ProfileViewModelTests: XCTestCase {
	private var profileViewModel: ProfileViewModel!
	
	private var userDetailsUseCase: MockUserDetailsUseCase!
	private var connectWalletUseCase: MockConnectWalletUseCase!
	private var changePasswordUseCase: MockChangePasswordUseCase!
	private var errorLogger: MockErrorLogger!
	
	override func setUp() async throws {
		try await super.setUp()
		
		userDetailsUseCase = MockUserDetailsUseCase(mockUser: mockUser, fetchLatency: fetchLatency)
		connectWalletUseCase = MockConnectWalletUseCase()
		changePasswordUseCase = MockChangePasswordUseCase()
		errorLogger = MockErrorLogger()
		
		Resolver.root = .mock
		setUpDI()
		profileViewModel = ProfileViewModel()
		let (cancellable, expectation) = loadingToastCountExpectation(expected: [false], vm: profileViewModel)
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
				if expected.first == value {
					expected.removeFirst()
				}
				if expected.isEmpty {
					expectation.fulfill()
				}
			}
		return (cancellable, expectation)
	}
	
	func testLoadingUserDetails() async throws {
		print("testing")

		let newViewModel = ProfileViewModel()
		
		let (cancellable, expectation) = loadingToastCountExpectation(expected: [true, false], vm: newViewModel)
		
		await newViewModel.loadUser()
		
		await fulfillment(of: [expectation], timeout: 0.1)
		
		cancellable.cancel()
	}
	
	func testChangePassword() async throws {
		let (cancellable, expectation) = loadingToastCountExpectation(expected: [true, false], vm: profileViewModel)
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
	
	func testWalletConnection() {
		let walletConnectedExpectation = expectation(description: "walletConnected")
		let walletDisconnectedExpectation = expectation(description: "walletDisconnected")
		walletDisconnectedExpectation.expectedFulfillmentCount = 2
		
		let cancellable = profileViewModel.$isWalletConnected.sink { isWalletConnected in
			if isWalletConnected {
				walletConnectedExpectation.fulfill()
			} else {
				walletDisconnectedExpectation.fulfill()
			}
		}
		
		connectWalletUseCase.connect(xpub: "xpub")
		NotificationCenter.default.post(name: NSNotification.Name(Notification().walletConnectionStateChanged), object: nil)
		wait(for: [walletConnectedExpectation], timeout: 1)
		profileViewModel.disconnectWallet()
		NotificationCenter.default.post(name: NSNotification.Name(Notification().walletConnectionStateChanged), object: nil)
		wait(for: [walletDisconnectedExpectation], timeout: 1)
		cancellable.cancel()
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

private extension ProfileViewModelTests {
	func setUpDI() {
		Resolver.mock.register {
			self.userDetailsUseCase as UserDetailsUseCase
		}
		
		Resolver.mock.register {
			self.connectWalletUseCase as ConnectWalletUseCase
		}
		
		Resolver.mock.register {
			self.changePasswordUseCase as ChangePasswordUseCase
		}
		
		Resolver.mock.register {
			self.errorLogger as ErrorReporting
		}
	}
}
