#if DEBUG
import Foundation
import SwiftUI
import ModuleLinker
import Resolver
import shared
import SharedUI

class MockArtistViewUIModelProviding: ArtistViewUIModelProviding {
	func getModel() async throws -> ArtistViewUIModel {
		Self.mockUIModel
	}
	
	static var mockUIModel: ArtistViewUIModel {
		ArtistViewUIModel(
			title: "J-ROC",
			headerImageSection: HeaderImageCellModel(headerImage: "bowie"),
			profileImageSection: ProfileImageCellModel(profileImage: "bowie"),
			followSection: SupportButton.followButton(),
			supportSection: SupportButton.supportButton(),
			trackSection: CellsSectionModel<BigCellViewModel>(cells: MockData.bigArtistCells, title: "LATEST TRACKS"),
			topSongsSection: CellsSectionModel<BigCellViewModel>(cells: MockData.bigArtistCells, title: "TOP SONGS"),
			albumSection: CellsSectionModel<BigCellViewModel>(cells: MockData.bigArtistCells, title: "ALBUMS")
		)
	}
}
#endif
