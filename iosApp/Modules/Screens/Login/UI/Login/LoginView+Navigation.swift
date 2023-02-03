//import Foundation
//import SwiftUI
//import Resolver
//import ModuleLinker
//import SwiftUINavigation
//
//extension LoginView {
//	struct Links: View {
//		@Binding var route: LoginRoute?
//		
//		var body: some View {
//			ZStack {
//				NavigationLink(unwrapping: $route,
//							   case: /LoginRoute.createAccount,
//							   destination: { _ in
//					CreateAccountView(viewModel: CreateAccountViewModel())
//				}, onNavigate: clearLinks, label: {})
//			}
//		}
//		
//		private func clearLinks(isActive: Bool) {
//			if isActive == false { route = nil }
//		}
//	}
//}
