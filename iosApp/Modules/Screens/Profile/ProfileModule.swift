import Foundation
import ModuleLinker
import Resolver
import shared

public final class ProfileModule: Module {
	public static let shared = ProfileModule()
	
	public func registerAllServices() {
		Resolver.register {
			ChangePasswordUseCaseProvider().get() as ChangePasswordUseCase
		}
	}
	
	public func registerAllMockedServices(mockResolver: Resolver) {
		
	}
}
