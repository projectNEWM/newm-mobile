import Foundation
import SharedUI

struct ArtistViewUIModel {
	let title: String
	let headerImageSection: HeaderImageCellModel
	let profileImageSection: ProfileImageCellModel
	let followSection: SupportButtonsCellModel
	let supportSection: SupportButtonsCellModel
	let trackSection: CellsSectionModel<BigCellViewModel>
	let topSongsSection: CellsSectionModel<BigCellViewModel>
	let albumSection: CellsSectionModel<BigCellViewModel>
}

protocol ArtistViewUIModelProviding {
	func getModel() async throws -> ArtistViewUIModel
}
