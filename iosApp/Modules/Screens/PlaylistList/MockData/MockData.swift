#if DEBUG
import Foundation
import ModuleLinker
import Resolver

class MockPlaylistListUseCase: PlaylistListUseCaseProtocol {
	func execute() -> [Playlist] {
		Resolver.resolve()
	}
}
#endif
