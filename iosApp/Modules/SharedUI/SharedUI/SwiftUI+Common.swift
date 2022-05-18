import Foundation
import SwiftUI

//TODO: Remove this protocol
public protocol DataView: View {
	init(id: String)
}

public struct CircleImage: View {
	let image: UIImage
	let size: CGFloat
	
	public init(image: UIImage, size: CGFloat) {
		self.image = image
		self.size = size
	}
	
	public var body: some View {
		Image(uiImage: image)
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
