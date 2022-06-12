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

extension LibraryModule: LibraryViewProviding {
	public func libraryView() -> AnyView {
		LibraryView().erased
	}
}

#if DEBUG
extension LibraryModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
	}
}
#endif
