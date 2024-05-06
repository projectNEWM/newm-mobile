import SwiftUI

public struct Toast: View {
	@Environment(\.colorScheme) var colorScheme
	
	public enum ToastType {
		case loading
		case complete
	}
	
	private var toastType: ToastType
	
	public init(toastType: ToastType) {
		self.toastType = toastType
	}
	
	public var body: some View {
		ZStack {
			Color.black.opacity(0.5)
			contentView
				.frame(width: 100, height: 100)
				.background(.gray)
				.opacity(0.5)
				.cornerRadius(10)
				.tint(tint)
		}
		.ignoresSafeArea()
	}
	
	@ViewBuilder
	private var contentView: some View {
		switch toastType {
		case .loading:
			ProgressView()
		case .complete:
			Text("✓")
				.font(.newmTitle1)
				.foregroundColor(.white)
				.backgroundStyle(.white)
				.opacity(1.0)
		}
	}
	
	private var tint: Color {
		return switch colorScheme {
		case .dark:
				.white
		case .light:
				.black
		@unknown default:
				.black
		}
	}
}

struct ToastModifier: ViewModifier {
	@Binding var shouldShow: Bool
	let toastType: Toast.ToastType
	
	func body(content: Content) -> some View {
		ZStack {
			content
				.disabled(shouldShow)
				.blur(radius: shouldShow ? 3 : 0)
			
			if shouldShow {
				Toast(toastType: toastType)
			}
		}
	}
}

public extension View {
	func toast(shouldShow: Binding<Bool>, type: Toast.ToastType) -> some View {
		modifier(ToastModifier(shouldShow: shouldShow, toastType: type))
	}
	
	func loadingToast(shouldShow: Binding<Bool>) -> some View {
		modifier(ToastModifier(shouldShow: shouldShow, toastType: .loading))
	}
}

struct Toast_Previews: PreviewProvider {
	static var previews: some View {
		Toast(toastType: .loading)
			.previewDisplayName("loading - light")
		Toast(toastType: .complete)
			.previewDisplayName("complete - light")
		
		Group {
			Toast(toastType: .loading)
				.previewDisplayName("loading - dark")
			Toast(toastType: .complete)
				.previewDisplayName("complete - dark")
		}
		.preferredColorScheme(.dark)
	}
}
