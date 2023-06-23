import SwiftUI
import SharedUI

extension LandingView {
	@ViewBuilder
	var createAccountView: some View {
		VStack {
			Asset.Media.logo.swiftUIImage.resizable().frame(width: 100, height: 100).padding(.top, 50).padding(.bottom, 25)
			VStack {
				NEWMTextField(title: .email, prompt: .emailPrompt, isSecure: false, text: $viewModel.email).padding(.bottom)
				NEWMTextField(title: .password, prompt: .yourPassword, isSecure: true, text: $viewModel.password).padding(.bottom)
				NEWMTextField(title: .password, prompt: .confirmPassword, isSecure: true, text: $viewModel.confirmPassword).padding(.bottom)
			}.padding()
			
			actionButton(title: .next) {
				viewModel.requestVerificationCode()
			}
			.disabled(!viewModel.createAccountFieldsAreValid)
			.padding([.leading, .trailing])
			
			Spacer()
		}
		.autocorrectionDisabled()
		.autocapitalization(.none)
	}
}
