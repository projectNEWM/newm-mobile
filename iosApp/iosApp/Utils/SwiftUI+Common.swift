import Foundation
import SwiftUI

struct SectionHeader: View {
	let title: String
	
	var body: some View {
		Text(title)
			.frame(maxWidth: .infinity, alignment: .leading)
			.foregroundColor(.white)
			.padding(.top)
			.padding(.leading)
			.font(.newmFont(ofSize: 16))
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
			.frame(maxWidth: size, maxHeight: size, alignment: .center)
			.aspectRatio(1, contentMode: .fit)
			.cornerRadius(size / 2.0)
	}
}
