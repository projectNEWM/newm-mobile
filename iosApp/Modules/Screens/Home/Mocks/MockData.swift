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
		HomeViewUIModel(
			greeting: TitleSectionModel(isGreeting: true, title: "HEY MIAH", profilePic: URL(string: "")),
			title: TitleSectionModel(isGreeting: false, title: "HOME", profilePic: URL(string: "")),
			thisWeekSection: ThisWeekSectionModel(title: "THIS WEEK", newFollowers: 12, royalties: 51.56, earnings: 2.15),
			recentlyPlayedSection: CellsSectionModel(cells: MockData.bigSongCells_shuffled(seed: 1, onTap: {_ in}), title: "RECENTLY PLAYED"),
			justReleasedSection: CellsSectionModel(cells: MockData.bigArtistCells_shuffled(seed: 1, onTap: {_ in}), title: "JUST RELEASED"),
			moreOfWhatYouLikeSection: CellsSectionModel(cells: MockData.bigArtistCells_shuffled(seed: 2, onTap: {_ in}), title: "MORE OF WHAT YOU LIKE"),
			newmArtistsSection: CellsSectionModel(cells: MockData.bigArtistCells_shuffled(seed: 3, onTap: {_ in}), title: "NEWM ARTISTS"),
			mostPopularThisWeek: CellsSectionModel(cells: MockData.bigArtistCells_shuffled(seed: 4, onTap: {_ in}), title: "MOST POPULAR THIS WEEK"),
			discoverTitle: "DISCOVER"
		)
	}
}
#endif
