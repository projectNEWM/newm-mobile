import Foundation
import SharedUI
import shared

struct HomeViewUIModel {
	let greeting: TitleSectionModel
	let title: TitleSectionModel
	let thisWeekSection: ThisWeekSectionModel
	let recentlyPlayedSection: [Song]
	let justReleasedSection: [Artist]
	let moreOfWhatYouLikeSection: [Artist]
	let newmArtistsSection: CellsSectionModel<BigCellViewModel>
	let mostPopularThisWeek: [Artist]
	let thisWeekTitle: String
	let discoverTitle: String
	let justReleasedTitle: String
	let moreOfWhatYouLikeTitle: String
	let newmArtistsTitle: String
	let mostPopularThisWeekTitle: String
	let recentlyPlayedTitle: String
}

struct ThisWeekSectionModel {
	let newFollowers: Int
	let royalties: Float
	let earnings: Float
}

protocol HomeViewUIModelProviding {
	func getModel() async throws -> HomeViewUIModel
}
