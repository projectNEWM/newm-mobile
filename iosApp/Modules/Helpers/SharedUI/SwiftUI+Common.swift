import Foundation
import SwiftUI
import ModuleLinker
import Colors

public extension Image {
	func circleImage(size: CGFloat) -> some View {
		self
			.resizable()
			.circle(size: size)
	}
}

public extension View {
	func circle(size: CGFloat) -> some View {
		self
			.frame(maxWidth: size, maxHeight: size, alignment: .center)
			.aspectRatio(1, contentMode: .fit)
			.cornerRadius(size / 2.0)
	}
}

public extension View {
	var sidePadding: CGFloat { 24 }
	private func sectionTitleFont() -> some View {
		font(.inter(ofSize: 12)).foregroundColor(NEWMColor.grey100())
	}
	
	func addSectionTitle(_ title: String) -> some View {
		VStack(alignment: .leading) {
			Text(title).sectionTitleFont()
				.padding(.leading, sidePadding)
			self
		}
	}
}

public extension View {
	func links<LinksView: View>(_ links: LinksView) -> some View {
		ZStack {
			links
			self
		}
	}
}

public extension Identifiable where Self: Hashable {
	var id: Self { self }
}

public extension ImageAsset {
	func callAsFunction() -> SwiftUI.Image {
		swiftUIImage
	}
}

public extension ColorAsset {
	func callAsFunction() -> SwiftUI.Color {
		swiftUIColor
	}
}
