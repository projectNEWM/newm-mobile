import Foundation
import SwiftUI

struct BackButtonBar: ViewModifier {
	let withToolbar: Bool
	
	func body(content: Content) -> some View {
		let modContent = content
			.navigationBarBackButtonHidden(true)
			.navigationBarItems(leading: BackButton())
		if withToolbar {
			return modContent
				.toolbarBackground(.visible, for: .navigationBar)
				.toolbarBackground(Color.black, for: .navigationBar)
				.erased
		} else {
			return modContent.erased
		}
	}
}

public struct BackButton: View {
	@Environment(\.presentationMode) @Binding var presentationMode: PresentationMode
	
	public init() {}
	
	public var body: some View {
		Button(action: { presentationMode.dismiss() }) {
			HStack {
				Asset.Media.backArrow()
					.aspectRatio(contentMode: .fit)
					.foregroundStyle(.white)
			}
		}
	}
}

public extension View {
	func backButtonBar(withToolbar: Bool = false) -> some View {
		modifier(BackButtonBar(withToolbar: withToolbar))
	}
}
