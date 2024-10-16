import Foundation
import SwiftUI
import Colors
import SharedUI
import Resolver

extension LandingView {
	@ViewBuilder
	var nicknameView: some View {
		VStack {
			Text(verbatim: .whatShouldWeCallYou)
				.font(.ralewayExtraBold(ofSize: 40))
				.multilineTextAlignment(.center)
				.fixedSize(horizontal: false, vertical: true)

			TextEditor(text: $viewModel.nickname)
				.foregroundStyle(Gradients.loginGradient.gradient)
				.multilineTextAlignment(.center)
				.font(.dmSerifItalic(ofSize: 40))
				.padding()
				.frame(height: 300)
				.accentColor(NEWMColor.pink())
				.focused($isTextFieldFocused)
				.autocorrectionDisabled()
				.autocapitalization(.none)
				.italic()

			Spacer()
			
			actionButton(title: .next, backgroundGradient: Gradients.loginGradient.gradient) {
				viewModel.registerUser()
			}
			.disabled(!viewModel.nicknameIsValid)
		}
		.padding()
		.onAppear {
			isTextFieldFocused = true
		}
	}
}

#if DEBUG
struct UsernameView_Previews: PreviewProvider {
	static var previews: some View {
		Resolver.root = Resolver(child: .main)
		LoginModule.shared.registerAllMockedServices(mockResolver: .root)
		return LandingView()
			.nicknameView
			.preferredColorScheme(.dark)
	}
}
#endif
