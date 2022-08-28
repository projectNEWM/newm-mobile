import Foundation
import shared
import SharedUI

struct LibraryViewUIModel {
    let greeting: TitleSectionModel
    let title: TitleSectionModel
    let recentlyPlayedSection: CellsSectionModel<BigCellViewModel>
    let yourPlaylistsSection: CellsSectionModel<CompactCellViewModel>
    let likedSongsSection: CellsSectionModel<BigCellViewModel>
}

public protocol GetRecentlyPlayedArtistsUseCase {
	func execute() -> [Artist]
}

public protocol GetYourPlaylistsUseCase {
	func execute() -> [Playlist]
}

public protocol GetLikedSongsUseCase {
	func execute() -> [Song]
}

protocol LibraryViewUIModelProviding {
    func getModel() async throws -> LibraryViewUIModel
}
