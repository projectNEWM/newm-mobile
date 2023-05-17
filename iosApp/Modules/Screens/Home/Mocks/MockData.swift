import Foundation
import SwiftUI
import ModuleLinker
import Resolver
import SharedUI
import Colors

struct MockHomeViewUIModelProvider: HomeViewUIModelProviding {
	let actionHandler: HomeViewActionHandling
	
	func getModel() async throws -> HomeViewUIModel {
		Self.mockUIModel(actionHandler: actionHandler)
	}
	
	static func mockUIModel(actionHandler: HomeViewActionHandling) -> HomeViewUIModel {
		func bigSongCells(seed: UInt64) -> [BigCellViewModel] {
			MockData.bigSongCells_shuffled(seed: seed) { songId in
				actionHandler.songTapped(id: songId)
			}
		}
		
		func bigArtistCells(seed: UInt64) -> [BigCellViewModel] {
			MockData.bigArtistCells_shuffled(seed: seed) { artistId in
				actionHandler.artistTapped(id: artistId)
			}
		}
		
		let justReleasedSection = CellsSectionModel(cells: bigArtistCells(seed: 4), title: "JUST RELEASED")
		let moreOfWhatYouLikeSection = CellsSectionModel(cells: bigArtistCells(seed: 3), title: "MORE OF WHAT YOU LIKE")
		let newmArtistsSection = CellsSectionModel(cells: bigArtistCells(seed: 2), title: "NEWM ARTISTS")
		let mostPopularThisWeek = CellsSectionModel(cells: bigArtistCells(seed: 1), title: "MOST POPULAR THIS WEEK")
		let recentlyPlayedSection = CellsSectionModel(cells: bigSongCells(seed: 1), title: "RECENTLY PLAYED")
		
		return HomeViewUIModel(
			greeting: HomeTitleSectionModel(isGreeting: true, title: "HEY MIAH", profilePic: TitleSection.ProfilePic.show(URL(string: "https://resizing.flixster.com/xhyRkgdbTuATF4u0C2pFWZQZZtw=/300x300/v2/https://flxt.tmsimg.com/assets/p175884_k_v9_ae.jpg")), gradientHexColors: Gradients.homeTitleGradient),
			title: HomeTitleSectionModel(isGreeting: false, title: "HOME", profilePic: TitleSection.ProfilePic.show(URL(string: "https://resizing.flixster.com/xhyRkgdbTuATF4u0C2pFWZQZZtw=/300x300/v2/https://flxt.tmsimg.com/assets/p175884_k_v9_ae.jpg")), gradientHexColors: Gradients.homeTitleGradient),
			thisWeekSection: ThisWeekSectionModel(title: "THIS WEEK", cells: SharedUI.MockData.thisWeekCells),
			recentlyPlayedSection: recentlyPlayedSection,
			justReleasedSection: justReleasedSection,
			moreOfWhatYouLikeSection: moreOfWhatYouLikeSection,
			newmArtistsSection: newmArtistsSection,
			mostPopularThisWeek: mostPopularThisWeek,
			discoverTitle: "DISCOVER"
		)
	}
}

//for xcode previews
class MockHomeActionHandler: HomeViewActionHandling {
	func artistTapped(id: String) {}
	func songTapped(id: String) {}
}
