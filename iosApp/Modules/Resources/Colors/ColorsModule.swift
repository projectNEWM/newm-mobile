import Foundation
import Resolver

public final class ColorsModule: ModuleProtocol {
	public static let shared = ColorsModule()
	
	public func registerAllServices() {
	}
}

#if DEBUG
extension ColorsModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
	}
}
#endif
