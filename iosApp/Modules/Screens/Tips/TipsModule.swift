import Foundation
import ModuleLinker
import Resolver

public final class TipsModule: ModuleProtocol {
	public static var shared = TipsModule()
	
	public func registerAllServices() {
		
	}
	
	public func registerAllMockedServices(mockResolver: Resolver) {
		
	}
}
