import Foundation
import Combine
import Resolver
import ModuleLinker
import SwiftUI
import shared
import Utilities
import SharedUI

public class HomeViewModel: ObservableObject {
	@Published var route: HomeRoute?
	@MainActor @Published var state: ViewState<(HomeViewActionHandler, HomeViewUIModel)> = .loading
//	var actionHandler: HomeViewActionHandler?

	@Injected private var uiModelProvider: HomeViewUIModelProvider
	@Injected private var actionHandlerProvider: HomeViewActionHandlerProvider
	
	init() {
		refresh()
	}
	
	func refresh() {
		//TODO: SHOULD THIS BE MAIN ACTOR...? COMPLAINS IF NOT
		Task { @MainActor in
			do {
				state = .loading
				let uiModel = try await uiModelProvider.getModel()
				let actionHandler = actionHandlerProvider.getActionHandler()
				state = .loaded((actionHandler, uiModel))
			} catch {
				state = .error(error)
			}
		}
	}
}

extension ThisWeekCellModel: Identifiable {
	var id: ObjectIdentifier { labelText.objectIdentifier }
}
