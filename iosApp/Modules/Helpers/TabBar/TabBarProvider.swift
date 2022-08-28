import Foundation
import SwiftUI

public struct TabViewProvider {
	public let image: Image
	public let tabName: String
	public let viewProvider: () -> AnyView
	
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
