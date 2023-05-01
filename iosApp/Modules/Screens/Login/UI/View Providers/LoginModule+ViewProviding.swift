import Foundation
import ModuleLinker
import SwiftUI

extension LoginModule: LoginViewProviding {
	public func loginView() -> AnyView {
		NavigationView {
			LandingView()
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
