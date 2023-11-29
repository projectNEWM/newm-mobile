import Foundation
import ModuleLinker
import Resolver
import SwiftUI

public final class PlaylistModule: Module {
	public static var shared = PlaylistModule()
	
	public func registerAllServices() {
		Resolver.register {
			self as PlaylistListViewProviding
		}
		
		Resolver.register {
			self as PlaylistViewProviding
		}
	}
	
//#if DEBUG
	public func registerAllMockedServices(mockResolver: Resolver) {
		mockResolver.register {
			MockPlaylistListUseCase()
		}
	}
//#endif
}

extension PlaylistModule: PlaylistListViewProviding {
	public func playlistListView() -> AnyView {
		PlaylistListView().erased
	}
}

extension PlaylistModule: PlaylistViewProviding {
	public func playlistView(id: String) -> AnyView {
		EmptyView().erased
	}
}
