import Foundation
import SwiftUI
import ModuleLinker

public extension Image {
	func circleImage(size: CGFloat) -> some View {
		self
			.resizable()
			.frame(maxWidth: size, maxHeight: size, alignment: .center)
			.aspectRatio(1, contentMode: .fit)
			.cornerRadius(size / 2.0)
	}
}
