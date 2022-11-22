import Foundation
import UIKit
import Combine
import Resolver
import ModuleLinker
import shared
import SharedUI

class ArtistViewModel: ObservableObject {
	@MainActor @Published var state: ViewState<(ArtistViewUIModel, ArtistViewActionHandling)> = .loading
	@Published var route: ArtistRoute?
	
	@Injected private var uiModelProvider: ArtistViewUIModelProviding

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

extension ArtistViewModel: ArtistViewActionHandling {
	func albumTapped(id: String) {
		print(#function + " " + id)
		route = .album(id: id)
	}
	
	func songTapped(id: String) {
		print(#function + " " + id)
		route = .songPlaying(id: id)
	}
	
	func songPlayingTapped(id: String) {
		print(#function + " " + id)
		route = .songPlaying(id: id)
	}
}
