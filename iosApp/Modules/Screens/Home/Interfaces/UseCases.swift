import Foundation
import shared

public protocol GetArtistsUseCase {
	func execute() -> [Artist]
}

public protocol GetSongsUseCase {
	func execute() -> [Song]
}

public protocol GetPlaylistsUseCase {
	func execute() -> [Playlist]
}
