import Foundation
import ModuleLinker
import Utilities

extension String {
	@Localizable(LibraryModule.self) static var library = "LIBRARY"
	@Localizable(LibraryModule.self) static var recentlyPlayed = "RECENTLY_PLAYED"
	@Localizable(LibraryModule.self) static var yourPlaylists = "YOUR_PLAYLISTS"
	@Localizable(LibraryModule.self) static var likedSongs = "LIKED_SONGS"
}
