import Foundation
import Resolver
import ModuleLinker
import Main
//import Wallet
import Home
import SharedUI
import SwiftUI
import Fonts
import Login
import NowPlaying
import PlaylistList
import Tips
import Artist
import Library
import AudioPlayer
//import Marketplace
import Data

struct iOSAppModule: Module {
	static var shared = iOSAppModule()
	
	let modules: [Module] = {
		[
//			WalletModule.shared,
			HomeModule.shared,
            LibraryModule.shared,
			SharedUIModule.shared,
			MainModule.shared,
			FontsModule.shared,
			LoginModule.shared,
			NowPlayingModule.shared,
			PlaylistModule.shared,
			TipsModule.shared,
			ArtistModule.shared,
			AudioPlayerModule.shared,
//			MarketplaceModule.shared
			DataModule.shared
		]
	}()
}

extension Resolver: ResolverRegistering {
	public static func registerAllServices() {
		iOSAppModule.shared.registerAllServices()
#if DEBUG
		Resolver.root.add(child: .mock)
		iOSAppModule.shared.registerAllMockedServices(mockResolver: .mock)
#endif
	}
}

extension iOSAppModule {
	func registerAllServices() {
		modules.forEach { $0.registerAllServices() }
	}

#if DEBUG
	func registerAllMockedServices(mockResolver: Resolver) {
		modules.forEach { $0.registerAllMockedServices(mockResolver: .mock) }
	}
#endif
}
