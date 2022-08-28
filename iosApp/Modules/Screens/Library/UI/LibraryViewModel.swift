import Foundation
import Combine
import Resolver
import ModuleLinker
import shared
import Utilities
import SharedUI
import Artist

class LibraryViewModel: ObservableObject {
    @MainActor @Published var state: ViewState<(LibraryViewUIModel, LibraryViewActionHandling)> = .loading
    @Published var route: LibraryRoute?
    
    @Injected private var uiModelProvider: LibraryViewUIModelProviding
    
    init() {
        Task {
            await refresh()
        }
    }
    
    @MainActor
    func refresh() async {
        do {
            state = .loading
            let uiModel = try await uiModelProvider.getModel()
            state = .loaded((uiModel, self))
        } catch {
            state = .error(error)
        }
    }
    
}

extension LibraryViewModel: LibraryViewActionHandling {
    func artistTapped(id: String) {
        print(#function + " " + id)
        route = .artist(id: id)
    }
    
    func songTapped(id: String) {
        print(#function + " " + id)
        route = .songPlaying(id: id)
    }
}

//
//	@Injected private var recentlyPlayedArtistsUseCase: GetRecentlyPlayedArtistsUseCase
//	@Injected private var yourPlaylistsUseCase: GetYourPlaylistsUseCase
//	@Injected private var likedSongsUseCase: GetLikedSongsUseCase
//
//	let title: String = .library
//
//	let recentlyPlayedSectionTitle: String = .recentlyPlayed
//	let yourPlaylistsSectionTitle: String = .yourPlaylists
//	let likedSongsSectionTitle: String = .likedSongs
//
//	@Published var recentlyPlayedArtists: [BigCellViewModel] = []
//	@Published var yourPlaylists: [CompactCellViewModel] = []
//	@Published var likedSongs: [BigCellViewModel] = []
//
//	init() {
//		refresh()
//	}
//
//    func refresh() {
//        recentlyPlayedArtists = recentlyPlayedArtistsUseCase.execute().map(BigCellViewModel.init)
//        yourPlaylists = yourPlaylistsUseCase.execute().map(CompactCellViewModel.init)
//        likedSongs = likedSongsUseCase.execute().map(BigCellViewModel.init)
//    }
//}
