import Foundation
import ModuleLinker
import Resolver
import SwiftUI
import Models

public final class ArtistModule: Module {
	public static var shared = ArtistModule()
	
	public func registerAllServices() {
		Resolver.register {
			self as ArtistViewProviding
		}
	}
	
//#if DEBUG
	public func registerAllMockedServices(mockResolver: Resolver) {
		mockResolver.register {
			MockArtistViewUIModelProviding() as ArtistViewUIModelProviding
		}
		
		mockResolver.register {
			MockArtistRepo() as ArtistRepo
		}
	}
//#endif
}

extension Song: Identifiable {}
extension Artist: Identifiable {}

