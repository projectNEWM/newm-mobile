import SwiftUI

public struct LoadingToast: View {
	public init() {}
	
	public var body: some View {
		ZStack {
			Color.black.opacity(0.5)
			ProgressView()
				.frame(width: 100, height: 100)
				.background(.gray)
				.opacity(0.5)
				.cornerRadius(10)
				.foregroundColor(.white)
		}
		.ignoresSafeArea()
	}
}

struct LoadingToastModifier: ViewModifier {
	@Binding var isLoading: Bool

	func body(content: Content) -> some View {
		ZStack {
			content
				.disabled(isLoading)
				.blur(radius: isLoading ? 3 : 0)

			if isLoading {
				Color.black.opacity(0.4)
					.edgesIgnoringSafeArea(.all)
					.transition(.opacity)
					.animation(.default)
				VStack {
					ProgressView()
						.progressViewStyle(CircularProgressViewStyle())
						.padding()
						.background(Color.white)
						.cornerRadius(10)
				}
				.padding()
			}
		}
	}
}

public extension View {
	func loadingToast(isLoading: Binding<Bool>) -> some View {
		modifier(LoadingToastModifier(isLoading: isLoading))
	}
}

struct LoadingToast_Previews: PreviewProvider {
	static var previews: some View {
		LoadingToast()
	}
}
