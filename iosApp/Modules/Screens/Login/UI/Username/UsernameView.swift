import Foundation
import SwiftUI
import Colors

struct UsernameView: View {
	@StateObject var viewModel = UsernameViewModel()
	@FocusState private var isTextFieldFocused: Bool

	var body: some View {
		VStack {
			Spacer()
			
			Text("What should we call you?")
				.font(.ralewayExtraBold(ofSize: 40))
				.multilineTextAlignment(.center)

			TextEditor(text: $viewModel.username)
				.foregroundStyle(Gradients.loginGradient.gradient)
				.multilineTextAlignment(.center)
				.font(.dmSerifItalic(ofSize: 40))
				.padding()
				.frame(height: 300)
				.accentColor(NEWMColor.pink.swiftUIColor)
				.focused($isTextFieldFocused)

			Spacer()
			
			Button {
				viewModel.nextTapped()
			} label: {
				buttonText("Next")
					.accentColor(.white)
					.background(Gradients.loginGradient.gradient)
			}
			.disabled(!viewModel.isValid())
			.cornerRadius(4)
			.links(Links(route: $viewModel.route))
			.padding(.bottom)
		}
		.padding()
		.backButton()
		.onAppear {
			isTextFieldFocused = true
		}
	}
}

struct UsernameView_Previews: PreviewProvider {
	static var previews: some View {
		UsernameView()
			.preferredColorScheme(.dark)
	}
}
