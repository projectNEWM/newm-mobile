import Foundation
import ModuleLinker
import Resolver
import SwiftUI
import shared

public final class SongPlayingModule: ModuleProtocol {
	public static var shared = SongPlayingModule()
	
	public func registerAllServices() {
		Resolver.register {
			self as SongPlayingViewProviding
		}
		
		Resolver.register {
			self as MinimizedNowPlayingViewProviding
		}
	}
}

extension SongPlayingModule: SongPlayingViewProviding {
	public func songPlayingView() -> AnyView {
		SongPlayingView().erased
	}
}

extension SongPlayingModule: MinimizedNowPlayingViewProviding {
	public func minimizedNowPlayingView() -> AnyView {
		MinimizedPlayerView().erased
	}
}

#if DEBUG
extension SongPlayingModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
	}
}
#endif
