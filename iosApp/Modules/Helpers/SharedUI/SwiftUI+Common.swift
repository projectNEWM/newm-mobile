import Foundation
import SwiftUI
import ModuleLinker

public struct CircleImage: View {
	let image: Image
	let size: CGFloat
	
	public init(_ image: UIImage, size: CGFloat) {
		self.image = Image(uiImage: image)
		self.size = size
	}
	
	public init(_ image: Image, size: CGFloat) {
		self.image = image
		self.size = size
	}

	public var body: some View {
		image
			.circleImage(size: size)
	}
}

extension Image {
	func circleImage(size: CGFloat) -> some View {
		self
			.resizable()
			.frame(maxWidth: size, maxHeight: size, alignment: .center)
			.aspectRatio(1, contentMode: .fit)
			.cornerRadius(size / 2.0)
	}
}
