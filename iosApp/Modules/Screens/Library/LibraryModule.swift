import Foundation
import Resolver
import ModuleLinker
import SwiftUI

public final class LibraryModule: ModuleProtocol {
	public static let shared = LibraryModule()
		
	public func registerAllServices() {
		Resolver.register {
			self as LibraryViewProviding
		}
		
        Resolver.register {
            LibraryViewModel()
        }
    }
}

#if DEBUG
extension LibraryModule {
    public func registerAllMockedServices(mockResolver: Resolver) {
        mockResolver.register {
            MockLibraryViewUIModelProviding() as LibraryViewUIModelProviding
        }
    }
}
#endif
