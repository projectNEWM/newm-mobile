import Foundation
import SwiftUI

struct TopToolbar: ViewModifier {
	@ViewBuilder let toolbarContent: () -> any View
	
	func body(content: Content) -> some View {
		VStack {
			// Toolbar at the top
			toolbarContent()
				.erased
				.padding()
			
			content
		}
	}
}

public extension View {
	func topToolbar(@ViewBuilder toolbarContent: @escaping () -> any View) -> some View {
		modifier(TopToolbar(toolbarContent: toolbarContent))
	}
}

public extension View {
	func moreButtonTopRight(action: @escaping () -> ()) -> some View {
		self.topToolbar {
			HStack {
				Spacer()
				Image(systemName: "ellipsis").rotationEffect(.degrees(90))
					.onTapGesture(perform: action)
			}
		}
	}
}
