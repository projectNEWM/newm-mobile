import Foundation
import SwiftUI

struct TabViewProvider {
	let image: Image
	let tab: MainViewModelTab
	let viewProvider: () -> AnyView
	let tint: Color
	
	init(
		image: Image,
		tab: MainViewModelTab,
		tint: Color,
		viewProvider: @escaping () -> AnyView
	) {
		self.image = image
		self.tab = tab
		self.viewProvider = viewProvider
		self.tint = tint
	}
}
