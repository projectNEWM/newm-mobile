import Foundation
import Resolver
import ModuleLinker
import SwiftUI

public final class DataModule: Module {
	public static let shared = DataModule()
	
	public func registerAllServices() {
	}
}

//#if DEBUG
extension DataModule {
	public func registerAllMockedServices(mockResolver: Resolver) {		
	}
}
//#endif
