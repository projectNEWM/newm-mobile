import Foundation
import Resolver
import ModuleLinker
import SwiftUI

public final class FontsModule: ModuleProtocol {
	public static var shared = FontsModule()
	
	public func registerAllServices() {
		Resolver.register {
			self as FontProviding
		}
	}
}

#if DEBUG
extension FontsModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
		
	}
}
#endif
