import Foundation
import ModuleLinker
import Resolver
import VLCKitSPM

public final class AudioPlayerModule: Module {
	public static var shared = AudioPlayerModule()
	
	public func registerAllServices() {
		Resolver.register { resolver in
			VLCAudioPlayer.sharedPlayer
		}.scope(.application)
	}
#if DEBUG
	public func registerAllMockedServices(mockResolver: Resolver) {
		
	}
#endif
}
