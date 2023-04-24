import SwiftUI
import SharedUI
import Colors
import ModuleLinker

public struct LandingView: View {
	@Binding private var shouldShow: Bool
	
	@StateObject var viewModel = LandingViewModel()
	@FocusState var isTextFieldFocused: Bool
	
	init(shouldShow: Binding<Bool>) {
		self._shouldShow = shouldShow
	}
		
	public var body: some View {
		ZStack {
			NavigationStack(path: $viewModel.navPath) {
				landingView
					.padding()
					.navigationDestination(for: LandingRoute.self) { route in
						Group {
							switch route {
							case .createAccount:
								createAccountView
							case .codeConfirmation:
								codeConfirmationView
							case .username:
								usernameView
							case .done:
								doneView
							case .login:
								loginView
							}
						}
						.backButton()
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
		FacebookLoginButton()
			.frame(height: 40)
			.addSidePadding()
			.background(Color(red: 24 / 255, green: 119 / 255, blue: 242 / 255))
			.cornerRadius(4)
			.padding(.top)
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
		LandingView(shouldShow: .constant(true))
			.preferredColorScheme(.dark)
	}
}
