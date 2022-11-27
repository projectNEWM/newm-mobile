import Foundation
import SwiftUI

struct BackButton: ViewModifier {
	@Environment(\.presentationMode) @Binding var presentationMode: PresentationMode
	
	func body(content: Content) -> some View {
		content
			.navigationBarBackButtonHidden(true)
			.navigationBarItems(leading: btnBack)
	}
	
	var btnBack: some View {
		Button(action: { presentationMode.dismiss() }) {
			HStack {
				Asset.Media.backArrow()
					.aspectRatio(contentMode: .fit)
					.foregroundColor(.white)
			}
		}
	}
}

public extension View {
	func backButton() -> some View {
		modifier(BackButton())
	}
}
