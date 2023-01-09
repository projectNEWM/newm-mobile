#if DEBUG
import Foundation
import SwiftUI
import ModuleLinker
import Resolver
import shared
import SharedUI
import Colors

struct MockLibraryViewUIModelProvider: LibraryViewUIModelProviding {
	let actionHandler: LibraryViewActionHandling
	
    func getModel() async throws -> LibraryViewUIModel {
        Self.mockUIModel(actionHandler: actionHandler)
    }
    
	static func mockUIModel(actionHandler: LibraryViewActionHandling) -> LibraryViewUIModel {
        LibraryViewUIModel(
			title: LibraryTitleSectionModel(title: "LIBRARY", gradientColors: Gradients.libraryGradient),
			recentlyPlayedSection: CellsSectionModel<BigCellViewModel>(cells: MockData.bigSongCells_shuffled(seed: 1, onTap: actionHandler.songTapped), title: "RECENTLY PLAYED"),
			yourPlaylistsSection: CellsSectionModel<BigCellViewModel>(cells: MockData.bigArtistCells_shuffled(seed: 0, onTap: actionHandler.artistTapped), title: "YOUR PLAYLISTS"),
			likedSongsSection: CellsSectionModel<BigCellViewModel>(cells: MockData.bigSongCells_shuffled(seed: 2, onTap: actionHandler.songTapped), title: "LIKED SONGS")
        )
    }
}

#endif
