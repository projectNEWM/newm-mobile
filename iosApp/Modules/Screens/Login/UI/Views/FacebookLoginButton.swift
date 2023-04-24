import SwiftUI
import FacebookLogin

struct FacebookLoginButton: UIViewRepresentable {
	func makeCoordinator() -> Coordinator {
		Coordinator(self)
	}

	func makeUIView(context: Context) -> FBLoginButton {
		let button = FBLoginButton()
		button.permissions = ["public_profile", "email"]
		button.delegate = context.coordinator
		return button
	}

	func updateUIView(_ uiView: FBLoginButton, context: Context) {
	}

	class Coordinator: NSObject, LoginButtonDelegate {
		var parent: FacebookLoginButton

		init(_ parent: FacebookLoginButton) {
			self.parent = parent
		}

		func loginButton(_ loginButton: FBLoginButton, didCompleteWith result: LoginManagerLoginResult?, error: Error?) {
			if let error = error {
				print("Error occurred during login: \(error.localizedDescription)")
			} else if let result = result, !result.isCancelled {
				print("Logged in successfully")
				// Handle successful login, e.g., fetch user data or navigate to another view
			} else {
				print("Login cancelled")
			}
		}

		func loginButtonDidLogOut(_ loginButton: FBLoginButton) {
			print("Logged out")
			// Handle logout, e.g., navigate to a login view
		}
	}
}
