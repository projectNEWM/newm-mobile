import Foundation
import SwiftUI

struct BackButton: ViewModifier {
	@Environment(\.presentationMode) @Binding var presentationMode: PresentationMode
	let withToolbar: Bool
	
	func body(content: Content) -> some View {
		let modContent = content
			.navigationBarBackButtonHidden(true)
			.navigationBarItems(leading: btnBack)
		if withToolbar {
			return modContent
				.toolbarBackground(.visible, for: .navigationBar)
				.toolbarBackground(Color.black, for: .navigationBar)
				.erased
		} else {
			return modContent.erased
		}
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
	func backButton(withToolbar: Bool = false) -> some View {
		modifier(BackButton(withToolbar: withToolbar))
	}
}
