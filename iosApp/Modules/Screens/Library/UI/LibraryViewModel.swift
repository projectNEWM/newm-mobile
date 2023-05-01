import Foundation
import Combine
import Resolver
import ModuleLinker
import SharedUI
import AudioPlayer

class LibraryViewModel: ObservableObject {
	@MainActor @Published var state: ViewState<(LibraryViewUIModel, LibraryViewActionHandling)> = .loading
	@Published var route: LibraryRoute?
	@Injected private var audioPlayer: AudioPlayerImpl
	
	private var uiModelProvider: LibraryViewUIModelProviding { MockLibraryViewUIModelProvider(actionHandler: self) }

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

extension LibraryViewModel: LibraryViewActionHandling {
	func playlistTapped(id: String) {
		route = .playlist(id: id)
	}
	
	func artistTapped(id: String) {
		route = .artist(id: id)
	}
	
	func songTapped(id: String) {
		audioPlayer.song = MockData.song(withID: id)
		audioPlayer.playbackInfo.isPlaying = true
	}
}
