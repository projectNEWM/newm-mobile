import Foundation
import SharedUI
import ModuleLinker

struct LibraryTitleSectionModel {
	let title: String
	let gradientColors: [String]
}

struct LibraryViewUIModel {
    let title: LibraryTitleSectionModel
    let recentlyPlayedSection: CellsSectionModel<BigCellViewModel>
    let yourPlaylistsSection: CellsSectionModel<BigCellViewModel>
    let likedSongsSection: CellsSectionModel<BigCellViewModel>
}

protocol LibraryViewUIModelProviding {
    func getModel() async throws -> LibraryViewUIModel
}
