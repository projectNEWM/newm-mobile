import SwiftUI
import Colors

public func actionButton(title: String, action: @escaping () -> ()) -> some View {
	Button(action: action) {
		buttonText(title)
			.background(Gradients.loginGradient.gradient)
	}
	.accentColor(.white)
	.cornerRadius(4)
	.padding([.bottom, .top])
}

@ViewBuilder
public func buttonText(_ text: String) -> some View {
	Text(verbatim: text)
		.padding()
		.frame(maxWidth: .infinity)
		.bold()
}
