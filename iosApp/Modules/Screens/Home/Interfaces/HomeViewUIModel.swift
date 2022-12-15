import Foundation
import SharedUI
import shared
import ModuleLinker

struct HomeTitleSectionModel {
	let isGreeting: Bool
	let title: String
	let profilePic: TitleSection.ProfilePic
	let gradientHexColors: [String]
}

struct HomeViewUIModel {
	let greeting: HomeTitleSectionModel
	let title: HomeTitleSectionModel
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
