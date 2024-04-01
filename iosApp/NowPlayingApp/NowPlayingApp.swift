import SwiftUI
import NowPlaying
import Resolver
import ModuleLinker
import AudioPlayer
import shared
import Logging

private let useMocks = false

@main
struct NowPlayingApp: App {
	init() {
		NowPlayingModule.shared.registerAllServices()
		AudioPlayerModule.shared.registerAllServices()
				
		LoggingModule.shared.registerAllServices()
		LoggingModule.shared.registerAllMockedServices(mockResolver: .mock)

		if useMocks {
			NowPlayingModule.shared.registerAllMockedServices(mockResolver: .mock)
			AudioPlayerModule.shared.registerAllMockedServices(mockResolver: .mock)
			Resolver.root = .mock
		}
		
		Resolver.resolve(VLCAudioPlayer.self).setTracks(Set(NFTTrack.mocks), playFirstTrack: true)
	}
	
    var body: some Scene {
        WindowGroup {
			Resolver.resolve(NowPlayingViewProviding.self).nowPlayingView()
        }
    }
}
