import Foundation
import ModuleLinker
import Resolver

final public class FilesModule: Module {
	public static var shared = FilesModule()
	
	public func registerAllServices() {
	}
	
#if DEBUG
	public func registerAllMockedServices(mockResolver: Resolver) {
		
	}
#endif
}
