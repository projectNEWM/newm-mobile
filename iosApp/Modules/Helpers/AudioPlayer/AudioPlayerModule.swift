import Foundation
import ModuleLinker
import Resolver
import VLCKitSPM

public final class AudioPlayerModule: Module {
	public static var shared = AudioPlayerModule()
	
	public func registerAllServices() {
		Resolver.register { resolver in
			VLCAudioPlayer.shared
		}.scope(.application)
	}
	
	public func registerAllMockedServices(mockResolver: Resolver) {
		
	}
}
