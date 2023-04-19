import SwiftUI
import SharedUI

extension LandingView {
	@ViewBuilder
	var createAccountView: some View {
		if let error = viewModel.error {
			Text(error)
		} else {
			VStack {
				Asset.Media.logo.swiftUIImage.resizable().frame(width: 100, height: 100).padding(.top, 50).padding(.bottom, 25)
				VStack {
					LoginTextField(title: .email, prompt: .emailPrompt, isSecure: false, text: $viewModel.email).padding(.bottom)
					LoginTextField(title: .password, prompt: .yourPassword, isSecure: true, text: $viewModel.password).padding(.bottom)
					LoginTextField(title: .password, prompt: .confirmPassword, isSecure: true, text: $viewModel.confirmPassword).padding(.bottom)
				}.padding()
				
				nextButton(title: .next) {
					viewModel.requestVerificationCode()
				}
				.disabled(!viewModel.loginFieldsAreValid)
				
				Spacer()
			}
			.autocorrectionDisabled()
			.autocapitalization(.none)
		}
	}
}
