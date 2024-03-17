import SwiftUI
import NowPlaying
import Resolver
import ModuleLinker
import AudioPlayer
import shared

@main
struct NowPlayingAppApp: App {
	init() {
		Resolver.root = .mock
		NowPlayingModule.shared.registerAllServices()
		NowPlayingModule.shared.registerAllMockedServices(mockResolver: .mock)
		AudioPlayerModule.shared.registerAllServices()
		AudioPlayerModule.shared.registerAllMockedServices(mockResolver: .mock)
		Resolver.resolve(VLCAudioPlayer.self).setTracks(Set(NFTTrackMocksKt.mockTracks), playFirstTrack: true)

	}
	
    var body: some Scene {
        WindowGroup {
			Resolver.resolve(NowPlayingViewProviding.self).nowPlayingView()
        }
    }
}
