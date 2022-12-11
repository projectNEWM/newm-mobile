import Foundation
import SharedUI
import shared
import ModuleLinker

struct HomeViewUIModel {
	let greeting: TitleSectionModel
	let title: TitleSectionModel
	let thisWeekSection: ThisWeekSectionModel
	let recentlyPlayedSection: CellsSectionModel<BigCellViewModel>
	let justReleasedSection: CellsSectionModel<BigCellViewModel>
	let moreOfWhatYouLikeSection: CellsSectionModel<BigCellViewModel>
	let newmArtistsSection: CellsSectionModel<BigCellViewModel>
	let mostPopularThisWeek: CellsSectionModel<BigCellViewModel>
	let discoverTitle: String
}

struct ThisWeekSectionModel {
	let title: String
	let newFollowers: Int
	let royalties: Float
	let earnings: Float
}

protocol HomeViewUIModelProviding {
	func getModel() async throws -> HomeViewUIModel
}
