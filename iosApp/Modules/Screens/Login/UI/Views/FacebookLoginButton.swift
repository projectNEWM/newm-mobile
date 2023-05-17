import SwiftUI
import FacebookLogin

struct FacebookLoginButton: UIViewRepresentable {
	enum FacebookError: Error {
		case authTokenMissing
		case cancelled
	}
	
	let logInCompletionHandler: (Result<LoginManagerLoginResult, Error>) -> ()
	let logOutCompletionHandler: () -> ()

	init(logInCompletionHandler: @escaping (Result<LoginManagerLoginResult, Error>) -> (), logOutCompletionHandler: @escaping () -> ()) {
		self.logInCompletionHandler = logInCompletionHandler
		self.logOutCompletionHandler = logOutCompletionHandler
	}

	func makeCoordinator() -> Coordinator {
		Coordinator(self, logInCompletionHandler: logInCompletionHandler, logOutCompletionHandler: logOutCompletionHandler)
	}

	func makeUIView(context: Context) -> FBLoginButton {
		let button = FBLoginButton()
		button.permissions = ["public_profile", "email"]
		button.delegate = context.coordinator
		return button
	}

	func updateUIView(_ uiView: FBLoginButton, context: Context) {}

	class Coordinator: NSObject, LoginButtonDelegate {
		let logInCompletionHandler: (Result<LoginManagerLoginResult, Error>) -> ()
		let logOutCompletionHandler: () -> ()

		var parent: FacebookLoginButton

		init(_ parent: FacebookLoginButton, logInCompletionHandler: @escaping (Result<LoginManagerLoginResult, Error>) -> (), logOutCompletionHandler: @escaping () -> ()) {
			self.parent = parent
			self.logInCompletionHandler = logInCompletionHandler
			self.logOutCompletionHandler = logOutCompletionHandler
		}

		func loginButton(_ loginButton: FBLoginButton, didCompleteWith result: LoginManagerLoginResult?, error: Error?) {
			if let error = error {
				print("Error occurred during login: \(error.localizedDescription)")
				logInCompletionHandler(.failure(error))
			} else if let result = result, !result.isCancelled {
				guard let token = result.token?.tokenString else {
					logInCompletionHandler(.failure(FacebookError.authTokenMissing))
					return
				}
				print("Logged in successfully")
				logInCompletionHandler(.success(result))
			} else {
				print("Login cancelled")
				logInCompletionHandler(.failure(FacebookError.cancelled))
			}
		}

		func loginButtonDidLogOut(_ loginButton: FBLoginButton) {
			print("Logged out")
			logOutCompletionHandler()
		}
	}
}
