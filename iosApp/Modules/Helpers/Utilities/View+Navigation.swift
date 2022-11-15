import Foundation
import SwiftUI

//TODO: move to SharedUI
public extension View {
	func links<LinksView: View>(_ links: LinksView) -> some View {
		ZStack {
			links
			self
		}
	}
}
