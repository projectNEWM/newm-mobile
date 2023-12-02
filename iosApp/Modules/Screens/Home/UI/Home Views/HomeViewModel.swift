import Foundation
import Combine
import Resolver
import ModuleLinker
import SharedUI
import AudioPlayer

@MainActor
class HomeViewModel: ObservableObject {
	@Published var state: ViewState<HomeViewUIModel> = .loading
	@Published var route = [HomeRoute]()
	@Published var shouldShowGreeting: Bool = true
	
	private var uiModelProvider: HomeViewUIModelProviding { MockHomeViewUIModelProvider(actionHandler: self) }
	@InjectedObject private var audioPlayer: VLCAudioPlayer

	init() {
		Task {
			resetGreetingTimer()
			await refresh()
		}
	}
	
	func refresh() async {
		do {
			state = .loading
			let uiModel = try await uiModelProvider.getModel()
			state = .loaded(uiModel)
		} catch {
			state = .error(error)
		}
	}
	
	func resetGreetingTimer() {
		shouldShowGreeting = true
		Task { @MainActor in
			try! await Task.sleep(nanoseconds: 3_000_000_000)
			shouldShowGreeting = false
		}
	}
}

extension HomeViewModel: HomeViewActionHandling {
	func artistTapped(id: String) {
		route.append(.artist(id: id))
	}
	
	func songTapped(id: String) {
		audioPlayer.song = MockData.song(withID: id)
		audioPlayer.playbackInfo.isPlaying = true
	}
	
	func profileTapped() {
		route.append(.profile)
	}
}
