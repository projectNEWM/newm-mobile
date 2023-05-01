import Foundation
import Combine
import Resolver
import ModuleLinker
import SharedUI
import Artist
import AudioPlayer
import Colors

class HomeViewModel: ObservableObject {
	@MainActor @Published var state: ViewState<HomeViewUIModel> = .loading
	@Published var route: HomeRoute?
	
	private var uiModelProvider: HomeViewUIModelProviding { MockHomeViewUIModelProvider(actionHandler: self) }
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
			state = .loaded(uiModel)
		} catch {
			state = .error(error)
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
