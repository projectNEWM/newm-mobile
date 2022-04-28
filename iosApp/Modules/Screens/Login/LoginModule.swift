import Foundation
import ModuleLinker
import Resolver
import SwiftUI

public final class LoginModule: ModuleProtocol {
	public static var shared = LoginModule()
	
	public func registerAllServices() {
		Resolver.register {
			self as LoginViewProviding
		}
	}
}

#if DEBUG
extension LoginModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
	}
}
#endif

extension LoginModule: LoginViewProviding {
	public func loginView() -> AnyView {
		LoginView().erased
	}
}
