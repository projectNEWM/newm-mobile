import Foundation
import SharedUI
import SwiftUI

struct ArtistViewUIModel {
	let title: String
	let headerImageSection: HeaderImageCellModel
	let profileImage: URL
	let followSection: any View
	let supportSection: any View
	let trackSection: CellsSectionModel<BigCellViewModel>
	let topSongsSection: CellsSectionModel<BigCellViewModel>
	let albumSection: CellsSectionModel<BigCellViewModel>
}

protocol ArtistViewUIModelProviding {
	func getModel(artistId: String) throws -> ArtistViewUIModel
}
