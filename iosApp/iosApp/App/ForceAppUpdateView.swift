import SwiftUI
import SharedUI
import Colors

struct ForceAppUpdateView: View {
	var body: some View {
		VStack {
			Spacer()
			Image("logo", bundle: Bundle(for: SharedUIModule.self))
			Text("An update is available")
				.font(.inter(ofSize: 24).bold())
				.padding()
			Text("To ensure the best performance and latest features, please update the app. The update might include new features, improved performance, bug fixes, and more!")
				.font(.interMedium(ofSize: 12))
			Spacer()
			Link("Update Now", destination: URL(string: "https://apps.apple.com/app/id6476981878")!)
				.frame(maxWidth: .infinity)
				.frame(height: 60)
				.background(Gradients.mainPrimary)
				.accentColor(.white)
				.cornerRadius(4)
				.bold()
		}
		.padding()
		.multilineTextAlignment(.center)
	}
}

#Preview {
	ForceAppUpdateView()
		.preferredColorScheme(.dark)
}
