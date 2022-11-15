import Foundation
import ModuleLinker
import Resolver
import SwiftUI
import shared

public final class NowPlayingModule: ModuleProtocol {
	public static var shared = NowPlayingModule()
	
	public func registerAllServices() {
		Resolver.register {
			self as NowPlayingViewProviding
		}
		
		Resolver.register {
			self as MinimizedNowPlayingViewProviding
		}
	}
}

extension NowPlayingModule: NowPlayingViewProviding {
	public func nowPlayingView() -> AnyView {
		NowPlayingView().erased
	}
}

extension NowPlayingModule: MinimizedNowPlayingViewProviding {
	public func minimizedNowPlayingView() -> AnyView {
		MinimizedPlayerView().erased
	}
}

#if DEBUG
extension NowPlayingModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
	}
}
#endif
