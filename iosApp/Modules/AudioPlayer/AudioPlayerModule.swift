import Foundation
import ModuleLinker
import Resolver
import VLCKitSPM
import Mocks

public final class AudioPlayerModule: Module {
	public static var shared = AudioPlayerModule()
	
	public func registerAllServices() {
		Resolver.register {
			VLCAudioPlayer.sharedPlayer
		}.scope(.application)
	}
#if DEBUG
	public func registerAllMockedServices(mockResolver: Resolver) {
		mockResolver.register {
			MockErrorLogger() as ErrorReporting
		}
	}
#endif
}
