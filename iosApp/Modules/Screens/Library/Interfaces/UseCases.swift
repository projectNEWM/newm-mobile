import Foundation
import shared

public protocol GetRecentlyPlayedArtistsUseCase {
	func execute() -> [Artist]
}

public protocol GetYourPlaylistsUseCase {
	func execute() -> [Playlist]
}

public protocol GetLikedSongsUseCase {
	func execute() -> [Song]
}
