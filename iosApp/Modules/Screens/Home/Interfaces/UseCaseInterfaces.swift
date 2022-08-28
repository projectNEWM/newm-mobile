import Foundation
import SharedUI

struct HomeViewUIModel {
	let greeting: TitleSectionModel
	let title: TitleSectionModel
	let thisWeekSection: ThisWeekSectionModel
	let recentlyPlayedSection: CellsSectionModel<BigCellViewModel>
	let justReleasedSection: CellsSectionModel<BigCellViewModel>
	let moreOfWhatYouLikeSection: CellsSectionModel<BigCellViewModel>
	let newmArtistsSection: CellsSectionModel<CompactCellViewModel>
	let mostPopularThisWeek: CellsSectionModel<BigCellViewModel>
	let thisWeekTitle: String
	let discoverTitle: String
	let justReleasedTitle: String
	let moreOfWhatYouLikeTitle: String
	let newmArtistsTitle: String
	let mostPopularThisWeekTitle: String
}

struct ThisWeekSectionModel {
	let newFollowers: Int
	let royalties: Float
	let earnings: Float
}

protocol HomeViewUIModelProviding {
	func getModel() async throws -> HomeViewUIModel
}
