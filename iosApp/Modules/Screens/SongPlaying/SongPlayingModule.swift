import Foundation
import ModuleLinker
import Resolver
import SwiftUI

public final class SongPlayingModule: ModuleProtocol {
	public static var shared = SongPlayingModule()
	
	public func registerAllServices() {
		Resolver.register {
			self as SongPlayingViewProviding
		}
	}
}

extension SongPlayingModule: SongPlayingViewProviding {
	public func songPlayingView(id: String) -> AnyView {
		SongPlayingView(id: id).erased
	}
}

#if DEBUG
extension SongPlayingModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
	}
}
#endif
