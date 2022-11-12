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
	}
}

extension SongPlayingModule: SongPlayingViewProviding {
	public func songPlayingView(song: Song) -> AnyView {
		SongPlayingView(song: song).erased
	}
}

#if DEBUG
extension SongPlayingModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
	}
}
#endif
