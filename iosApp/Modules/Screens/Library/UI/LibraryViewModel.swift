import Foundation
import ModuleLinker
import SharedUI
import Resolver

class LibraryViewModel: ObservableObject {
	@Injected private var recentlyPlayedArtistsUseCase: GetRecentlyPlayedArtistsUseCase
	@Injected private var yourPlaylistsUseCase: GetYourPlaylistsUseCase
	@Injected private var likedSongsUseCase: GetLikedSongsUseCase

	let title: String = .library
	
	let recentlyPlayedSectionTitle: String = .recentlyPlayed
	let yourPlaylistsSectionTitle: String = .yourPlaylists
	let likedSongsSectionTitle: String = .likedSongs
	
	@Published var route: LibraryRoute?
	
	@Published var recentlyPlayedArtists: [BigCellViewModel] = []
	@Published var yourPlaylists: [CompactCellViewModel] = []
	@Published var likedSongs: [BigCellViewModel] = []
	
	init() {
		refresh()
	}
	
	func refresh() {
		recentlyPlayedArtists = recentlyPlayedArtistsUseCase.execute().map(BigCellViewModel.init)
		yourPlaylists = yourPlaylistsUseCase.execute().map(CompactCellViewModel.init)
		likedSongs = likedSongsUseCase.execute().map(BigCellViewModel.init)
	}
}
