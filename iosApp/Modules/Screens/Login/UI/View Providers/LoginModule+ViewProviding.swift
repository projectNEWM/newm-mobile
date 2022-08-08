import Foundation
import ModuleLinker
import SwiftUI

extension LoginModule: LoginViewProviding {
	public func loginView() -> AnyView {
		LoginView().erased
	}
}

extension LoginModule: CreateAccountViewProviding {
	public func createAccountView() -> AnyView {
		CreateAccountView().erased
	}
}
