import SwiftUI
import Fonts

extension LandingView {
	@ViewBuilder
	var enterNewPasswordView: some View {
		ScrollView {
			Text("Enter your new password")
				.multilineTextAlignment(.center)
				.font(.ralewayExtraBold(ofSize: 40))
				.padding()
			LoginTextField(title: "AUTH CODE", prompt: "The code that was emailed to you", isSecure: true, text: $viewModel.confirmationCode).padding(.bottom)
			LoginTextField(title: "YOUR NEW PASSWORD", prompt: "Your new password", isSecure: true, text: $viewModel.password).padding(.bottom)
			LoginTextField(title: "CONFIRM PASSWORD", prompt: "Confirm your new password", isSecure: true, text: $viewModel.confirmPassword).padding(.bottom)
			nextButton(title: "Confirm") {
				viewModel.resetPassword()
			}
		}
		.addSidePadding()
	}
}
