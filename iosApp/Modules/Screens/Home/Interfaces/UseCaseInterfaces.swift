import Foundation
import SharedUI

struct HomeViewUIModel {
	struct ThisWeekSectionModel {
		let newFollowers: Int
		let royalties: Float
		let earnings: Float
	}
	
	struct TitleSectionModel {
		let isGreeting: Bool
		let title: String
		let profilePicURL: URL
	}

	let titleSectionModel: TitleSectionModel
	let greetingSectionModel: TitleSectionModel
	let thisWeekSection: ThisWeekSectionModel
	let recentlyPlayedSection: CellsSectionModel<BigCellViewModel>
	let justReleasedSection: CellsSectionModel<BigCellViewModel>
	let moreOfWhatYouLikeSection: CellsSectionModel<BigCellViewModel>
	let newmArtistsSection: CellsSectionModel<CompactCellViewModel>
	let mostPopularThisWeek: CellsSectionModel<BigCellViewModel>
}

protocol GetHomeViewUseCase {
	func execute() -> HomeViewUIModel
}
