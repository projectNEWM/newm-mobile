//import Foundation
//import SwiftUI
//import Resolver
//import ModuleLinker
//import SwiftUINavigation
//
//extension CreateAccountView {
//	struct Links: View {
//		@Binding var route: CreateAccountRoute?
//		
//		var body: some View {
//			ZStack {
//				NavigationLink(unwrapping: $route,
//							   case: /CreateAccountRoute.codeConfirmation,
//							   destination: { code in
//					
//				}, onNavigate: clearLinks, label: {})
//			}
//		}
//		
//		private func clearLinks(isActive: Bool) {
//			if isActive == false { route = nil }
//		}
//	}
//}
