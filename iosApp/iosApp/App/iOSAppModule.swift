import Foundation
import Resolver
import ModuleLinker
import Main
import SharedUI
import SwiftUI
import Fonts
import Login
import NowPlaying
import Library
import AudioPlayer
import Files
import shared
import Logging
import Profile

struct iOSAppModule: Module {
	static var shared = iOSAppModule()
	
	private let modules: [Module] = {
		[
            LibraryModule.shared,
			SharedUIModule.shared,
			MainModule.shared,
			FontsModule.shared,
			LoginModule.shared,
			NowPlayingModule.shared,
			AudioPlayerModule.shared,
			FilesModule.shared,
			LoggingModule.shared,
			ProfileModule.shared
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
		Resolver.register {
			UserDetailsUseCaseProvider().get() as UserDetailsUseCase
		}
		modules.forEach { $0.registerAllServices() }
	}

#if DEBUG
	func registerAllMockedServices(mockResolver: Resolver) {
		modules.forEach { $0.registerAllMockedServices(mockResolver: .mock) }
	}
#endif
}
