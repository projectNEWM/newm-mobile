import Foundation
import ModuleLinker
import Resolver

public final class AudioPlayerModule: ModuleProtocol {
	public static var shared = AudioPlayerModule()
	
	public func registerAllServices() {
		Resolver.register {
			AnyAudioPlayer(audioPlayer: AudioPlayerImpl.shared) as AnyAudioPlayer
		}.scope(.application)
	}
	
	public func registerAllMockedServices(mockResolver: Resolver) {
		
	}
}
