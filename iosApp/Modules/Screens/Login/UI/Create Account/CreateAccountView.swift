import Foundation
import SwiftUI
import Colors

struct CreateAccountView: View {
	@StateObject var viewModel = CreateAccountViewModel()
	
	var body: some View {
		if let error = viewModel.error {
			Text(error)
		} else {
			VStack {
				Asset.logo.swiftUIImage.resizable().frame(width: 100, height: 100).padding(.top, 50).padding(.bottom, 25)
				VStack {
					LoginTextField(title: .email, prompt: .emailPrompt, isSecure: false, text: $viewModel.email).padding(.bottom)
					LoginTextField(title: .password, prompt: .yourPassword, isSecure: true, text: $viewModel.password).padding(.bottom)
					LoginTextField(title: .password, prompt: .confirmPassword, isSecure: true, text: $viewModel.confirmPassword).padding(.bottom)
				}.padding()
					.links(Links(route: $viewModel.route))
				
				Button {
					viewModel.next()
				} label: {
					buttonText(.next)
				}
				.borderOverlay(color: NEWMColor.grey500(), radius: 4, width: 2)
				.accentColor(NEWMColor.pink())
				.padding([.leading, .trailing])
				.frame(height: 40)
				.disabled(!viewModel.nextEnabled)
				
				Spacer()
			}
			.autocorrectionDisabled()
			.autocapitalization(.none)
			.backButton()
		}
	}
}

struct CreateAccountView_PreviewProvider: PreviewProvider {
	static var previews: some View {
		CreateAccountView()
			.preferredColorScheme(.dark)
	}
}
