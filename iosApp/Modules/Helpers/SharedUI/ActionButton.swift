import SwiftUI
import Colors

public func actionButton(title: String, backgroundGradient: some View, action: @escaping () -> ()) -> some View {
	actionButton(title: Text(title), backgroundGradient: backgroundGradient, action: action)
}

public func actionButton(title: any View, backgroundGradient: some View, action: @escaping () -> ()) -> some View {
	Button(action: action) {
		buttonText(title, backgroundGradient: backgroundGradient)
	}
	.accentColor(.white)
	.padding([.bottom, .top])
}

@ViewBuilder
public func buttonText(_ text: String) -> some View {
	buttonText(Text(text))
}

@ViewBuilder
public func buttonText(_ text: any View, backgroundGradient: some View = EmptyView()) -> some View {
	text
		.padding()
		.frame(maxWidth: .infinity)
		.bold()
		.erased
		.background(backgroundGradient)
		.cornerRadius(4)
}

#Preview {
	VStack {
		actionButton(title: "Login", backgroundGradient: Gradients.loginGradient.gradient) {}
			.preferredColorScheme(.dark)
	}
}
