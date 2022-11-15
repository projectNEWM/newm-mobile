#if DEBUG
import Foundation
import SwiftUI
import ModuleLinker
import Resolver
import shared
import SharedUI

class MockHomeViewUIModelProvider: HomeViewUIModelProviding {
	func getModel() async throws -> HomeViewUIModel {
		Self.mockUIModel
	}
	
	static var mockUIModel: HomeViewUIModel {
		return HomeViewUIModel(
			greeting: TitleSectionModel(isGreeting: true, title: "HEY MIAH", profilePic: URL(string: "")),
			title: TitleSectionModel(isGreeting: false, title: "HOME", profilePic: URL(string: "")),
			thisWeekSection: ThisWeekSectionModel(newFollowers: 12, royalties: 51.56, earnings: 2.15),
			recentlyPlayedSection: MockData.songs,
			justReleasedSection: MockData.artists,//CellsSectionModel(cells: MockData.bigArtistCells, title: "JUST RELEASED"),
			moreOfWhatYouLikeSection: CellsSectionModel<BigCellViewModel>(cells: MockData.bigArtistCells, title: "MORE OF WHAT YOU LIKE"),
			newmArtistsSection: CellsSectionModel<CompactCellViewModel>(cells: MockData.compactArtistCells, title: "NEWM ARTISTS"),
			mostPopularThisWeek: CellsSectionModel<BigCellViewModel>(cells: MockData.bigArtistCells, title: "MOST POPULAR THIS WEEK"),
			thisWeekTitle: "THIS WEEK",
			discoverTitle: "DISCOVER",
			justReleasedTitle: "JUST RELEASED",
			moreOfWhatYouLikeTitle: "MORE OF WHAT YOU LIKE",
			newmArtistsTitle: "NEWM ARTISTS",
			mostPopularThisWeekTitle: "MOST POPULAR THIS WEEK"
		)
	}
}
#endif
