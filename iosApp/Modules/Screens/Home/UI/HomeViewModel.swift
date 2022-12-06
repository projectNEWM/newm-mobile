import Foundation
import Combine
import Resolver
import ModuleLinker
import shared
import SharedUI
import Artist
import AudioPlayer

class HomeViewModel: ObservableObject {
	@MainActor @Published var state: ViewState<(HomeViewUIModel, HomeViewActionHandling)> = .loading
	@Published var route: HomeRoute?
	
	private var uiModelProvider: HomeViewUIModelProviding & HomeViewActionHandling { self }
	@Injected private var audioPlayer: AudioPlayerImpl

	init() {
		Task {
			await refresh()
		}
	}
	
	@MainActor
	func refresh() async {
		do {
			state = .loading
			let uiModel = try await uiModelProvider.getModel()
			state = .loaded((uiModel, self))
		} catch {
			state = .error(error)
		}
	}
}

extension HomeViewModel: HomeViewUIModelProviding {
	func getModel() async throws -> HomeViewUIModel {
		let justReleasedSection = CellsSectionModel(cells: bigArtistCells(seed: 4), title: "JUST RELEASED")
		let moreOfWhatYouLikeSection = CellsSectionModel(cells: bigArtistCells(seed: 3), title: "MORE OF WHAT YOU LIKE")
		let newmArtistsSection = CellsSectionModel(cells: bigArtistCells(seed: 2), title: "NEWM ARTISTS")
		let mostPopularThisWeek = CellsSectionModel(cells: bigArtistCells(seed: 1), title: "MOST POPULAR THIS WEEK")
		let recentlyPlayedSection = CellsSectionModel(cells: bigSongCells(seed: 1), title: "RECENTLY PLAYED")
		
		return HomeViewUIModel(
			greeting: TitleSectionModel(isGreeting: true, title: "HEY MIAH", profilePic: URL(string: "https://resizing.flixster.com/xhyRkgdbTuATF4u0C2pFWZQZZtw=/300x300/v2/https://flxt.tmsimg.com/assets/p175884_k_v9_ae.jpg")),
			title: TitleSectionModel(isGreeting: false, title: "HOME", profilePic: URL(string: "https://resizing.flixster.com/xhyRkgdbTuATF4u0C2pFWZQZZtw=/300x300/v2/https://flxt.tmsimg.com/assets/p175884_k_v9_ae.jpg")),
			thisWeekSection: ThisWeekSectionModel(title: "THIS WEEK", newFollowers: 12, royalties: 51.56, earnings: 2.15),
			recentlyPlayedSection: recentlyPlayedSection,
			justReleasedSection: justReleasedSection,
			moreOfWhatYouLikeSection: moreOfWhatYouLikeSection,
			newmArtistsSection: newmArtistsSection,
			mostPopularThisWeek: mostPopularThisWeek,
			discoverTitle: "DISCOVER"
		)
	}
	
	private func bigSongCells(seed: UInt64) -> [BigCellViewModel] {
		MockData.bigSongCells_shuffled(seed: seed) { [weak self] songId in
			self?.songTapped(id: songId)
		}
	}
	
	private func bigArtistCells(seed: UInt64) -> [BigCellViewModel] {
		MockData.bigArtistCells_shuffled(seed: seed) { [weak self] artistId in
			self?.artistTapped(id: artistId)
		}
	}
}

extension HomeViewModel: HomeViewActionHandling {
	func artistTapped(id: String) {
		route = .artist(id: id)
	}
	
	func songTapped(id: String) {
		audioPlayer.song = MockData.song(withID: id)
		audioPlayer.playbackInfo.isPlaying = true
	}
}

extension ThisWeekCellModel: Identifiable {
	var id: String { labelText }
}
