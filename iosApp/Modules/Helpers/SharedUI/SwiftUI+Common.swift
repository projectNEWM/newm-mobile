import Foundation
import SwiftUI
import ModuleLinker
import Colors

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
		font(.inter(ofSize: 12)).foregroundColor(NEWMColor.grey100.swiftUIColor)
	}
	
	func addSectionTitle(_ title: String) -> some View {
		VStack(alignment: .leading) {
			Text(title).sectionTitleFont()
				.padding(.leading, sidePadding)
			self
		}
	}
}

public struct HorizontalScroller: View {
	public let title: String
	public let content: () -> any View
	
	public init(title: String, @ViewBuilder content: @escaping () -> (some View)) {
		self.title = title
		self.content = content
	}
	
	public var body: some View {
		ScrollView(.horizontal, showsIndicators: false) {
			content()
				.padding([.leading, .trailing], sidePadding)
				.fixedSize()
				.erased
		}
		.addSectionTitle(title)
	}
}
