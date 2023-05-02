import SwiftUI
import Colors

extension LandingView {
	@ViewBuilder
	var doneView: some View {
		VStack {
			Spacer()
			
			Text("Aaaaaand you're all done, \(viewModel.username)!")
					.font(.ralewayExtraBold(ofSize: 40))
					.padding(.bottom)

			Text(verbatim: .shallWe)
					.font(.dmSerifItalic(ofSize: 40))
					.foregroundStyle(Gradients.loginGradient.gradient)
					.italic()

			Spacer()

			Group {
				Text("By tapping 'Enter NEWM', you agree to NEWM's")
					.foregroundColor(NEWMColor.grey200.swiftUIColor)

				Text("Privacy Policy & Terms of Service.")
					.underline()
					.onTapGesture {
						//TODO: show privacy policy
					}
					.foregroundColor(.white)
			}
			.font(.interMedium(ofSize: 14))

			nextButton(title: .next) {
				viewModel.wantsToBeShown = false
			}
		}
		.navigationBarBackButtonHidden()
		.multilineTextAlignment(.center)
		.addSidePadding()
	}
}

struct DoneView_Previews: PreviewProvider {
	static var previews: some View {
		LandingView(shouldShow: .constant(true))
			.doneView
			.preferredColorScheme(.dark)
	}
}
