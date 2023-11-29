import Foundation
import Resolver

public final class ModuleLinkerModule: Module {
	public static var shared = ModuleLinkerModule()
	
	public func registerAllServices() {
		
	}
}

//#if DEBUG
extension ModuleLinkerModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
		
	}
}
//#endif
