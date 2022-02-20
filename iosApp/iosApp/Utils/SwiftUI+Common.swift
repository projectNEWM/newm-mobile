import Foundation
import SwiftUI

struct SectionHeader: View {
	let title: String
	
	var body: some View {
		Text(title)
			.frame(maxWidth: .infinity, alignment: .leading)
			.foregroundColor(.white)
			.padding(.top)
	}
}

protocol DataView: View {	
	init(id: String)
}

struct CircleImage: View {
	let image: UIImage
	let size: CGFloat
	
	var body: some View {
		Image(uiImage: image)
			.circleImage(size: size)
	}
}

extension Image {
	func circleImage(size: CGFloat) -> some View {
		self
			.resizable()
			.frame(width: size, height: size, alignment: .center)
			.cornerRadius(size / 2.0)
	}
}
