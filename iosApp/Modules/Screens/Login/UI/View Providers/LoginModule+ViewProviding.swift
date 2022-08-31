import Foundation
import ModuleLinker
import SwiftUI

extension LoginModule: LoginViewProviding {
	public func loginView() -> AnyView {
		LoginView().erased
	}
}
