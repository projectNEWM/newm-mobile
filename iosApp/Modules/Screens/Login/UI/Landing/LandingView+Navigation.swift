import Foundation
import Resolver
import SwiftUI
import SwiftUINavigation

extension LandingView {
	struct Links: View {
		@Binding var route: LandingRoute?
		
		var body: some View {
			ZStack {
				NavigationLink(unwrapping: $route,
							   case: /LandingRoute.login,
							   destination: { _ in
					LoginView()
						.backButton()
				}, onNavigate: clearLinks, label: {})
				
				NavigationLink(unwrapping: $route,
							   case: /LandingRoute.createAccount,
							   destination: { _ in
					CreateAccountView()
				}, onNavigate: clearLinks, label: {})
			}
		}
		
		private func clearLinks(isActive: Bool) {
			if isActive == false { route = nil }
		}
	}
}
