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

public extension View {
	var sidePadding: CGFloat { 24 }
	private func sectionTitleFont() -> some View {
		font(.inter(ofSize: 12)).foregroundColor(Color(.grey100))
	}
	
	private func addSectionTitle(_ title: String) -> some View {
		VStack(alignment: .leading) {
			Text(title).sectionTitleFont()
				.padding(.leading, sidePadding)
			self
		}
	}
	
	func addHorizontalScrollView(title: String) -> some View {
		ScrollView(.horizontal, showsIndicators: false) {
			self
			.padding([.leading, .trailing], sidePadding)
			.fixedSize()
		}
		.addSectionTitle(title)
	}
}
