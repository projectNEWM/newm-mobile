import SwiftUI
import SharedUI
import Colors
import ModuleLinker
import GoogleSignInSwift
import GoogleSignIn
import AuthenticationServices

public struct LandingView: View {
	@StateObject var viewModel = LandingViewModel()
	@FocusState var isTextFieldFocused: Bool
		
	public var body: some View {
		ZStack {
			NavigationStack(path: $viewModel.navPath) {
				landingView
					.padding()
					.navigationDestination(for: LandingRoute.self) { route in
						switch route {
						case .createAccount:
							createAccountView.backButton()
						case .codeConfirmation:
							codeConfirmationView.backButton()
						case .nickname:
							nicknameView.backButton()
						case .done:
							doneView
						case .login:
							loginView.backButton()
						}
					}
			}
			.alert(String.error, isPresented: isPresent($viewModel.error), actions: {}) {
				Text(viewModel.error ?? .unknownError)
			}
			
			if viewModel.isLoading {
				LoadingToast()
			}
		}
	}
}

//Landing View
extension LandingView {
	@ViewBuilder
	private var landingView: some View {
		VStack {
			Asset.Media.logo.swiftUIImage.padding().padding(.top, 40)
			title
			Spacer()
			Group {
				loginButton
				createAccountButton
				facebookLoginButton
				googleSignInButton
			}
			.cornerRadius(4)
			.font(.inter(ofSize: 14).weight(.semibold))
		}
	}
	
	@ViewBuilder
	private var title: some View {
		Text(verbatim: .welcomeToNewm)
			.font(.ralewayExtraBold(ofSize: 30))
	}
	
	@ViewBuilder
	private var loginButton: some View {
		nextButton(title: .login) {
			viewModel.goToLogin()
		}
	}
	
	@ViewBuilder
	private var createAccountButton: some View {
		Button {
			viewModel.createAccount()
		} label: {
			buttonText(.createNewAccount)
		}
		.background(.clear)
		.foregroundColor(NEWMColor.pink())
		.borderOverlay(color: NEWMColor.grey500(), radius: 4, width: 2)
	}
	
	@ViewBuilder
	private var facebookLoginButton: some View {
		FacebookLoginButton(logInCompletionHandler: viewModel.handleFacebookLogin, logOutCompletionHandler: viewModel.handleFacebookLogout)
			.frame(height: 40)
			.addSidePadding()
			.background(Color(red: 24 / 255, green: 119 / 255, blue: 242 / 255))
			.cornerRadius(4)
			.padding(.top)
	}
	
//	@ViewBuilder
//	private var signInWithAppleButton: some View {
//		SignInWithAppleButton(.signIn) { request in
//			request.requestedScopes = [.email, .fullName]
//		} onCompletion: { result in
//		case .success(let authResults):
//			print("Authorisation successful")
//			LoginManager.
//		case .error(let error):
//			print("Authorisation failed: \(error.localizedDescription)")
//		}
//
//	}
	
	private var rootViewController: UIViewController? {
		guard let rootVC = (UIApplication.shared.connectedScenes.first as? UIWindowScene)?.windows.first?.rootViewController else {
			//TODO: log error
			return nil
		}
		return rootVC
	}
	
	@ViewBuilder
	private var googleSignInButton: some View {
		if let rootVC = rootViewController {
			GoogleSignInButton {
				GIDSignIn.sharedInstance.signIn(withPresenting: rootVC, completion: viewModel.handleGoogleSignIn)
			}
		} else {
			EmptyView()
		}
	}
}

extension LandingView {
	@ViewBuilder
	func nextButton(title: String, action: @escaping () -> ()) -> some View {
		Button(action: action) {
			buttonText(title)
				.background(Gradients.loginGradient.gradient)
		}
		.accentColor(.white)
		.cornerRadius(4)
		.padding([.bottom, .top])
	}
}

struct LandingView_Previews: PreviewProvider {
	static var previews: some View {
		LandingView()
			.preferredColorScheme(.dark)
	}
}
