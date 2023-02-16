import Foundation
import SwiftUI
import Colors

struct CodeConfirmationView: View {
	@StateObject var viewModel = CodeConfirmationViewModel()
	
	init() {}
	
	var body: some View {
		ScrollView {
			VStack {
				Text("Check your inbox")
					.font(.ralewayExtraBold(ofSize: 40))
				
				Text("Enter your verification code below")
					.font(.dmSerifItalic(ofSize: 40))
					.multilineTextAlignment(.center)
					.foregroundStyle(Gradients.loginGradient.gradient)
					.padding(4)
				
				Spacer()
				
				Button("Didn't receive an email?  Tap here to resend.") {
					viewModel.resendCode()
				}
				.accentColor(.white)
				.font(.inter(ofSize: 14))
				.padding()
				
				LoginTextField(title: nil, prompt: "Verification Code", isSecure: false, text: $viewModel.code)
					.padding(.bottom)
				
				Button {
					viewModel.confirmTapped()
				} label: {
					buttonText("Continue")
				}
				.background(Gradients.loginGradient.gradient)
				.accentColor(.white)
				.cornerRadius(4)
				
				Spacer()
			}
			.padding()
			.backButton()
			.links(Links(route: $viewModel.route))
		}
	}
}

struct CodeConfirmation_Previews: PreviewProvider {
	static var previews: some View {
		CodeConfirmationView()
			.preferredColorScheme(.dark)
	}
}
