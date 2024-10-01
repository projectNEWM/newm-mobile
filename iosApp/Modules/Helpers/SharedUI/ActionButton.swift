import SwiftUI
import Colors

public func actionButton(title: String, backgroundGradient: LinearGradient, action: @escaping () -> ()) -> some View {
	actionButton(title: Text(title), backgroundGradient: backgroundGradient, action: action)
}

public func actionButton(title: any View, backgroundGradient: LinearGradient, action: @escaping () -> ()) -> some View {
	Button(action: action) {
		buttonText(title)
			.background(backgroundGradient)
	}
	.accentColor(.white)
	.cornerRadius(4)
	.padding([.bottom, .top])
}

@ViewBuilder
public func buttonText(_ text: String) -> some View {
	buttonText(Text(text))
}

@ViewBuilder
public func buttonText(_ text: any View) -> some View {
	text
		.padding()
		.frame(maxWidth: .infinity)
		.bold()
		.erased
}

#Preview {
	VStack {
		actionButton(title: "Login", backgroundGradient: Gradients.loginGradient.gradient) {}
			.preferredColorScheme(.dark)
	}
}
