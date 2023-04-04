import Foundation
import SwiftUI

public struct StickyView: ViewModifier {
	public func body(content: Content) -> some View {
		GeometryReader { geo in
			if(geo.frame(in: .global).minY <= 0) {
				content
					.offset(y: -geo.frame(in: .global).minY)
			} else {
				content
			}
		}
	}
}

extension View {
	public func sticky() -> some View {
		modifier(StickyView())
	}
}
