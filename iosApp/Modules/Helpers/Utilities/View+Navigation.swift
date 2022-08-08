import Foundation
import SwiftUI

public extension View {
	func links<LinksView: View>(_ links: LinksView) -> some View {
		ZStack {
			links
			self
		}
	}
}
