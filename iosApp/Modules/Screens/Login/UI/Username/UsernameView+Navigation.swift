import Foundation
import SwiftUI
import ModuleLinker
import SwiftUINavigation

extension UsernameView {
	struct Links: View {
		@Binding var route: UsernameRoute?

		var body: some View {
			ZStack {
				NavigationLink(unwrapping: $route,
							   case: /UsernameRoute.done,
							   destination: { username in
					DoneView(username: username.wrappedValue)
				}, onNavigate: clearLinks, label: {})
			}
		}

		private func clearLinks(isActive: Bool) {
			if isActive == false { route = nil }
		}
	}
}
