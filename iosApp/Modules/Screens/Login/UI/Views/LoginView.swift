import SwiftUI
import SharedUI

extension LandingView {
	@ViewBuilder
	var loginView: some View {
		ZStack {
			VStack {
				Asset.Media.logo.swiftUIImage.resizable().frame(width: 112, height: 112).padding(40)
				LoginTextField(title: .email, prompt: .emailPrompt, isSecure: false, text: $viewModel.email).padding(.bottom)
				LoginTextField(title: .password, prompt: .yourPassword, isSecure: true, text: $viewModel.password).padding(.bottom)
				nextButton(title: .next) {
					viewModel.login()
				}
				.disabled(!viewModel.loginFieldsAreValid)
				Spacer()
			}
			.addSidePadding()
		}
	}
}

struct LoginView_Previews: PreviewProvider {
	static var previews: some View {
		LandingView(shouldShow: .constant(true))
			.loginView
			.preferredColorScheme(.dark)
	}
}
