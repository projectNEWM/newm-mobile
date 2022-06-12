import Foundation

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
	let title: String
}

protocol GetHomeViewUseCase {
	func execute() -> HomeViewUIModel
}
