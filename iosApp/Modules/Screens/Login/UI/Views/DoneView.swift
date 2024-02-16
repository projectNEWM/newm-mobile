import SwiftUI
import Colors
import SharedUI

extension LandingView {
	@ViewBuilder
	var doneView: some View {
		VStack {
			Spacer()
			
			Text("Aaaaaand you're all done, \(viewModel.nickname)!")
					.font(.ralewayExtraBold(ofSize: 40))
					.padding(.bottom)

			Text(verbatim: .shallWe)
					.font(.dmSerifItalic(ofSize: 40))
					.foregroundStyle(Gradients.loginGradient.gradient)
					.italic()

			Spacer()

			Group {
				Text("By tapping 'Enter NEWM', you agree to NEWM's")
					.foregroundColor(NEWMColor.grey200())

				Text("Privacy Policy & Terms of Service.")
					.underline()
					.onTapGesture {
						//TODO: show privacy policy
					}
					.foregroundColor(.white)
			}
			.font(.interMedium(ofSize: 14))

			actionButton(title: .enterNewm) {
				Task { await viewModel.login() }
			}
		}
		.navigationBarBackButtonHidden()
		.multilineTextAlignment(.center)
		.addSidePadding()
	}
}

struct DoneView_Previews: PreviewProvider {
	static var previews: some View {
		LandingView()
			.doneView
			.preferredColorScheme(.dark)
	}
}
