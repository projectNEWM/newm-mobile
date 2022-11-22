import Foundation
import ModuleLinker
import Resolver
import SwiftUI

public final class ArtistModule: ModuleProtocol {
	public static var shared = ArtistModule()
	
	public func registerAllServices() {
		Resolver.register {
			self as ArtistViewProviding
		}
	}
	
#if DEBUG
	public func registerAllMockedServices(mockResolver: Resolver) {
		mockResolver.register {
			MockArtistViewUIModelProviding() as ArtistViewUIModelProviding
		}
	}
#endif
}
