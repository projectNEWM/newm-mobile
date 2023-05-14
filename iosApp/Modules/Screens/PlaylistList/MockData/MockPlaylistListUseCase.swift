import Foundation
import ModuleLinker
import Resolver
import Models

class MockPlaylistListUseCase: PlaylistListUseCaseProtocol {
	func execute() -> [Playlist] {
		[
			MockData.makePlaylist(id: "1"),
			MockData.makePlaylist(id: "2"),
			MockData.makePlaylist(id: "3"),
			MockData.makePlaylist(id: "4"),
			MockData.makePlaylist(id: "5"),
		]
	}
}
