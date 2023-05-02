import Foundation
import ModuleLinker
import SwiftUI

extension LoginModule: LoginViewProviding {
	public func loginView(shouldShow: Binding<Bool>) -> AnyView {
		NavigationView {
			LandingView(shouldShow: shouldShow)
		}
		.preferredColorScheme(.dark)
		.erased
	}
}

extension LoginModule: CreateAccountViewProviding {
	public func createAccountView() -> AnyView {
//		CreateAccountView().erased
		EmptyView().erased
	}
}
