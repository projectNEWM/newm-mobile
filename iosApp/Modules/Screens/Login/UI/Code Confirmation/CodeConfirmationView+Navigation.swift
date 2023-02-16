import Foundation
import SwiftUI
import Resolver
import ModuleLinker
import SwiftUINavigation

extension CodeConfirmationView {
	struct Links: View {
		@Binding var route: CodeConfirmationRoute?
		
		var body: some View {
			ZStack {
				NavigationLink(unwrapping: $route,
							   case: /CodeConfirmationRoute.username,
							   destination: { code in
					UsernameView()
				}, onNavigate: clearLinks, label: {})
			}
		}
		
		private func clearLinks(isActive: Bool) {
			if isActive == false { route = nil }
		}
	}
}
