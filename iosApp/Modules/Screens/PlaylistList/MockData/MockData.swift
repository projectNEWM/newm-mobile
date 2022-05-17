#if DEBUG
import Foundation
import ModuleLinker
import Resolver
import shared

class MockPlaylistListUseCase: PlaylistListUseCaseProtocol {
	func execute() -> [Playlist] {
		Resolver.resolve()
	}
}
#endif
