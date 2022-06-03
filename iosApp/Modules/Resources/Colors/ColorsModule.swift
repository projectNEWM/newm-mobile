import Foundation
import Resolver
import ModuleLinker

public final class ColorsModule: ModuleProtocol {
	public static let shared = ColorsModule()
	
	public func registerAllServices() {
		Resolver.register {
			self as ColorProviding
		}
	}
}

#if DEBUG
extension ColorsModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
	}
}
#endif
