import Foundation
import SwiftUI
import Colors
import SharedUI

extension LandingView {
	@ViewBuilder
	var codeConfirmationView: some View {
		ScrollView {
			VStack {
				Text("Check your inbox")
					.font(.ralewayExtraBold(ofSize: 40))
				
				Text("Enter your verification code below")
					.font(.dmSerifItalic(ofSize: 40))
					.italic()
					.multilineTextAlignment(.center)
					.foregroundStyle(Gradients.loginGradient.gradient)
					.padding(4)
				
				Spacer()
				Spacer()
				
				Button(String.resendEmail) {
					viewModel.requestVerificationCode()
				}
				.accentColor(.white)
				.font(.inter(ofSize: 14))
				.padding()

				NEWMTextField(title: nil, prompt: .verificationCode, isSecure: false, text: $viewModel.confirmationCode)
					.padding(.bottom)
				
				Button {
					viewModel.requestNickname()
				} label: {
					buttonText(.next)
				}
				.disabled(viewModel.confirmationCodeIsValid == false)
				.background(Gradients.loginGradient.gradient)
				.accentColor(.white)
				.cornerRadius(4)
				
				Spacer()
			}
			.padding()
		}
	}
}

struct CodeConfirmation_Previews: PreviewProvider {
	static var previews: some View {
		LandingView()
			.codeConfirmationView
			.preferredColorScheme(.dark)
	}
}
