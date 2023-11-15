import Foundation
import ModuleLinker
import Resolver

public final class AudioPlayerModule: Module {
	public static var shared = AudioPlayerModule()
	
	public func registerAllServices() {
		Resolver.register { resolver in
			let audioPlayer = AudioPlayer()
			audioPlayer.delegate = resolver.resolve(AudioPlayerDelegate.self)
			return audioPlayer
		}.scope(.application)
		
		Resolver.register {
			AudioPlayerPublisher()
		}.scope(.application)
		
		Resolver.register { resolver in
			resolver.resolve(AudioPlayerPublisher.self) as AudioPlayerDelegate
		}.scope(.application)
	}
	
	public func registerAllMockedServices(mockResolver: Resolver) {
		
	}
}
