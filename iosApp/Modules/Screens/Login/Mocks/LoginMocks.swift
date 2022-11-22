import Foundation
import ModuleLinker
import Combine
import UIKit

class MockLogInLogOutUseCase: LogInUseCaseProtocol, LogOutUseCaseProtocol, LoggedInUserUseCaseProtocol {
	private static let authCredsKey = "authCreds"

	/*@UserDefault(authCredsKey, defaultValue: nil)*/ private var authCreds: AuthCredentials?
	
	public var loggedInUser: AnyPublisher<String?, Never> { _loggedInUser.eraseToAnyPublisher() }
	private lazy var _loggedInUser = CurrentValueSubject<String?, Never>(authCreds?.user)
	static let shared = MockLogInLogOutUseCase()

	func logIn(email: String, password: String) async throws {
		let authTokens = AuthTokens(authToken: "laksjdflkj", refreshToken: "asdfo0asf")
		let user = email
		authCreds = AuthCredentials(authTokens: authTokens, user: user)
		_loggedInUser.send(user)
	}
	
	func logOut() {
		authCreds = nil
		_loggedInUser.send(nil)
	}
	
	private init() {
		NotificationCenter.default.addObserver(forName: .deviceDidShakeNotification, object: nil, queue: nil) { [weak self] _ in
			self?.logOut()
		}
	}
}

//TODO: remove this
extension NSNotification.Name {
	public static let deviceDidShakeNotification = NSNotification.Name("MyDeviceDidShakeNotification")
}

extension UIWindow {
	open override func motionEnded(_ motion: UIEvent.EventSubtype, with event: UIEvent?) {
		super.motionEnded(motion, with: event)
		NotificationCenter.default.post(name: .deviceDidShakeNotification, object: event)
	}
}
