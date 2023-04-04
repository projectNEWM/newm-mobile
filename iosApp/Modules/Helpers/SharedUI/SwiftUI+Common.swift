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
	
	func addSidePadding() -> some View {
		padding([.leading, .trailing], sidePadding)
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

public enum TextStyle {
	//[String] is hex values
	case screenTitle([String])
}

public extension Text {
	@ViewBuilder
	func style(_ style: TextStyle) -> some View {
		switch style {
		case .screenTitle(let hexStrings):
			self
				.font(.newmTitle1)
				.foregroundStyle(LinearGradient(colors: hexStrings.colors, startPoint: .leading, endPoint: .trailing))
		}
	}
}

public struct BorderOverlay: ViewModifier {
	let color: any ShapeStyle
	let radius: CGFloat
	let width: CGFloat
	
	public func body(content: Content) -> some View {
		content
			.overlay(RoundedRectangle(cornerRadius: radius)
				.stroke(color, lineWidth: width))
			.erased
	}
}

public extension View {
	func borderOverlay(color: any ShapeStyle, radius: CGFloat, width: CGFloat) -> some View {
		modifier(BorderOverlay(color: color, radius: radius, width: width))
	}
}

