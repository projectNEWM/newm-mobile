import Foundation
import SwiftUI

public struct TabViewProvider {
	let image: Image
	let tabName: String
	let viewProvider: () -> AnyView
	
	public init(
		image: Image,
		tabName: String,
		viewProvider: @escaping () -> AnyView
	) {
		self.image = image
		self.tabName = tabName
		self.viewProvider = viewProvider
	}
}
