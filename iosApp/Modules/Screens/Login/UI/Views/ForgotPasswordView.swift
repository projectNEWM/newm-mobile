import SwiftUI
import Fonts
import Colors

extension LandingView {
	@ViewBuilder
	var forgotPasswordView: some View {
		ScrollView {
			VStack {
				Text("Forgot Your Password?")
					.multilineTextAlignment(.center)
					.font(.ralewayExtraBold(ofSize: 40))
					.padding()
				Text("Enter your email to receive reset instructions.")
					.multilineTextAlignment(.center)
					.font(.dmSerifItalic(ofSize: 40))
					.foregroundStyle(Gradients.loginGradient.gradient)
					.padding()
					.padding(.bottom, 40)
				LoginTextField(title: .email, prompt: .emailPrompt, isSecure: false, text: $viewModel.email).padding(.bottom)
				nextButton(title: "Reset password") {
					viewModel.requestPasswordResetCode()
				}
			}
			.addSidePadding()
		}
	}
}

struct ForgotPassword_Previews: PreviewProvider {
	static var previews: some View {
		LandingView()
			.forgotPasswordView
			.preferredColorScheme(.dark)
	}
}
