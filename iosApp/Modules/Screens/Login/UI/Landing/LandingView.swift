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
	}
	
	@ViewBuilder
	private var title: some View {
		Text(verbatim: .welcomeToNewm)
			.font(.ralewayExtraBold(ofSize: 30))
	}
	
	@ViewBuilder
	private var loginButton: some View {
		HStack {
			Spacer()
			Button {
				viewModel.login()
			} label: {
				Text(verbatim: .login)
					.padding()
			}
			Spacer()
		}
		.background(Gradients.loginGradient.gradient)
		.foregroundColor(.white)
	}
	
	@ViewBuilder
	private var createAccountButton: some View {
		HStack {
			Spacer()
			Button {
				viewModel.createAccount()
			} label: {
				Text(verbatim: .createNewAccount)
					.padding()
			}
			Spacer()
		}
		.background(.clear)
		.foregroundColor(NEWMColor.pink.swiftUIColor)
		.borderOverlay(color: NEWMColor.grey500.swiftUIColor, width: 2)
	}
}

struct LandingView_Previews: PreviewProvider {
	static var previews: some View {
		LandingView()
			.preferredColorScheme(.dark)
	}
}
