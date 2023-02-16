import SwiftUI
import SharedUI
import Colors

struct LandingView: View {
	@StateObject private var viewModel = LandingViewModel()
	
	var body: some View {
		VStack {
			Asset.logo.swiftUIImage.padding().padding(.top, 40)
			title
			Spacer()
			Group {
				loginButton
				createAccountButton
			}
			.cornerRadius(4)
			.font(.inter(ofSize: 14).weight(.semibold))
		}
		.padding()
		.links(Links(route: $viewModel.route))
	}
	
	@ViewBuilder
	private var title: some View {
		Text(verbatim: .welcomeToNewm)
			.font(.ralewayExtraBold(ofSize: 30))
	}
	
	@ViewBuilder
	private var loginButton: some View {
		Button {
			viewModel.login()
		} label: {
			buttonText(.login)
		}
		.background(Gradients.loginGradient.gradient)
		.foregroundColor(.white)
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
		.borderOverlay(color: NEWMColor.grey500(), width: 2)
	}
}

struct LandingView_Previews: PreviewProvider {
	static var previews: some View {
		LandingView()
			.preferredColorScheme(.dark)
	}
}
