import Foundation
import ModuleLinker
import Resolver

final public class FilesModule: Module {
	public static var shared = FilesModule()
	
	public func registerAllServices() {
	}
	
	public func registerAllMockedServices(mockResolver: Resolver) {
		
	}
}
