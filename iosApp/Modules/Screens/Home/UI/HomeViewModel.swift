import Foundation
import Combine
import Resolver
import ModuleLinker
import shared
import SharedUI
import Artist

class HomeViewModel: ObservableObject {
	@MainActor @Published var state: ViewState<(HomeViewUIModel, HomeViewActionHandling)> = .loading
	@Published var route: HomeRoute?
	
	@Injected private var uiModelProvider: HomeViewUIModelProviding

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

extension HomeViewModel: HomeViewActionHandling {
	func artistTapped(id: String) {
		print(#function + " " + id)
		route = .artist(id: id)
	}
	
	func songTapped(id: String) {
		print(#function + " " + id)
		route = .nowPlaying
	}
}

extension ThisWeekCellModel: Identifiable {
	var id: String { labelText }
}
