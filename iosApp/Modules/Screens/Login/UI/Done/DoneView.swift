import SwiftUI
import Colors

struct DoneView: View {
	@StateObject var viewModel = DoneViewModel()
	
	var username: String
	
	var body: some View {
		VStack {
			Spacer()
			
			Text("Aaaaaand you're all done, \(username)!")
					.font(.ralewayExtraBold(ofSize: 40))

			Text("Shall we?")
					.font(.dmSerifItalic(ofSize: 40))
					.foregroundStyle(Gradients.loginGradient.gradient)

			Spacer()

			Group {
				Text("By tapping 'Enter NEWM', you agree to NEWM's")
				
				Text("Privacy Policy & Terms of Service.")
					.underline()
					.onTapGesture {
						//TODO: show privacy policy
					}
			}
			.foregroundColor(NEWMColor.grey200.swiftUIColor)
			.font(.interMedium(ofSize: 14))

			Button {
				viewModel.enterTapped()
			} label: {
				buttonText("Enter")
					.background(Gradients.loginGradient.gradient)
			}
			.accentColor(.white)
			.cornerRadius(4)
			.padding([.bottom, .top])
		}
		.navigationBarBackButtonHidden()
		.multilineTextAlignment(.center)
		.addSidePadding()
	}
}

struct DoneView_Previews: PreviewProvider {
	static var previews: some View {
		DoneView(username: "Joey")
			.preferredColorScheme(.dark)
	}
}
