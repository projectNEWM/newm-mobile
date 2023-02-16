import SwiftUI
import Colors
import SharedUI

struct LoginView: View {
	@StateObject private var viewModel = LoginViewModel()
	
	var body: some View {
		ZStack {
			VStack {
				Asset.logo.swiftUIImage.resizable().frame(width: 112, height: 112).padding(40)
				LoginTextField(title: .email, prompt: .emailPrompt, isSecure: false, text: $viewModel.email).padding(.bottom)
				LoginTextField(title: .password, prompt: .yourPassword, isSecure: true, text: $viewModel.password).padding(.bottom)
					.padding(.bottom, 40)
				nextButton
				Spacer()
			}
			.addSidePadding()
			
			if viewModel.isLoading {
				LoadingToast()
			}
		}
	}
	
	private var nextButton: some View {
		Button {
			viewModel.logIn()
		} label: {
			Text("\(.enter)")
				.frame(maxWidth: .infinity)
		}
		.font(.inter(ofSize: 14).weight(.semibold))
		.accentColor(.white)
		.addSidePadding()
		.padding([.top, .bottom], 10)
		.background(Gradients.loginGradient.gradient)
		.cornerRadius(4)
		.autocorrectionDisabled()
		.disabled(!viewModel.fieldsAreValid)
	}
}


struct LoginView_Previews: PreviewProvider {
	static var previews: some View {
		LoginView()
			.preferredColorScheme(.dark)
	}
}
