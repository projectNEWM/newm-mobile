import Foundation
import Combine
import Resolver
import ModuleLinker
import shared
import Utilities
import SharedUI
import Artist

class HomeViewModel: ObservableObject {
	@MainActor @Published var state: ViewState<HomeViewUIModel> = .loading
	@Published var route: HomeRoute?
	
	@Injected private var uiModelProvider: HomeViewUIModelProviding

	init() {
		refresh()
	}
	
	func refresh() {
		//TODO: SHOULD THIS BE MAIN ACTOR...? COMPLAINS IF NOT
		Task { @MainActor in
			do {
				state = .loading
				let uiModel = try await uiModelProvider.getModel()
				state = .loaded(uiModel)
			} catch {
				state = .error(error)
			}
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
		route = .songPlaying(id: id)
	}
}

extension ThisWeekCellModel: Identifiable {
	var id: ObjectIdentifier { labelText.objectIdentifier }
}
