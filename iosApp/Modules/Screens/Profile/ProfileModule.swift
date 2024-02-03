import Foundation
import ModuleLinker
import Resolver
import shared
import Mocks

public final class ProfileModule: Module {
	public static let shared = ProfileModule()
	
	public func registerAllServices() {
		Resolver.register {
			ChangePasswordUseCaseProvider().get() as ChangePasswordUseCase
		}
	}
	
#if DEBUG
	public func registerAllMockedServices(mockResolver: Resolver = .mock) {	}
#endif
}
