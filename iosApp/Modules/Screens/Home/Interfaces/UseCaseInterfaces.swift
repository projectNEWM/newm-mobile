import Foundation
import SharedUI

struct HomeViewUIModel {
	struct ThisWeekSectionModel {
		let newFollowers: Int
		let royalties: Float
		let earnings: Float
	}
	
	struct TitleSectionModel {
		let titleIsGreeting: Bool
		let title: String
		let profilePicURL: URL
	}

	let titleSectionModel: TitleSectionModel
	let thisWeekSection: ThisWeekSectionModel
	let recentlyPlayedSection: CellsSectionModel<BigCellViewModel>
	let justReleasedSection: CellsSectionModel<BigCellViewModel>
	let moreOfWhatYouLikeSection: CellsSectionModel<BigCellViewModel>
	let newmArtistsSection: CellsSectionModel<CompactCellViewModel>
	let mostPopularThisWeek: CellsSectionModel<BigCellViewModel>
	let title: String
}

protocol GetHomeViewUseCase {
	func execute() -> HomeViewUIModel
}
