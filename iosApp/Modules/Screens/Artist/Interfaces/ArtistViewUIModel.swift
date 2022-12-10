import Foundation
import SharedUI
import SwiftUI
import shared

struct ArtistViewUIModel {
	let title: String
	let headerImageUrl: String?
	let profileImage: URL
	let followSection: any View
	let supportSection: any View
	let trackSection: CellsSectionModel<BigCellViewModel>
	let topSongs: CellsSectionModel<BigCellViewModel>
	let albumSection: CellsSectionModel<BigCellViewModel>
}

protocol ArtistViewUIModelProviding {
	func getModel(artist: Artist, actionHandler: ArtistViewActionHandling) throws -> ArtistViewUIModel
}
