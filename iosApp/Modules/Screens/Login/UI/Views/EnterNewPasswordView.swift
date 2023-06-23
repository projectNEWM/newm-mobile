import SwiftUI
import Fonts
import SharedUI

extension LandingView {
	@ViewBuilder
	var enterNewPasswordView: some View {
		ScrollView {
			Text("Enter your new password")
				.multilineTextAlignment(.center)
				.font(.ralewayExtraBold(ofSize: 40))
				.padding()
			NEWMTextField(title: "AUTH CODE", prompt: "The code that was emailed to you", isSecure: true, text: $viewModel.confirmationCode).padding(.bottom)
			NEWMTextField(title: "YOUR NEW PASSWORD", prompt: "Your new password", isSecure: true, text: $viewModel.password).padding(.bottom)
			NEWMTextField(title: "CONFIRM PASSWORD", prompt: "Confirm your new password", isSecure: true, text: $viewModel.confirmPassword).padding(.bottom)
			actionButton(title: "Confirm") {
				viewModel.resetPassword()
			}
		}
		.addSidePadding()
	}
}
