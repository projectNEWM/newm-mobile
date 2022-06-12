import Foundation
import Combine
import Resolver
import ModuleLinker
import SwiftUI
import shared
import Utilities
import SharedUI

enum ViewState<Data> {
	case loading
	case loaded(Data)
	case error
}

public class HomeViewModel: ObservableObject {	
	let thisWeekTitle: String = .thisWeek
	let discoverTitle: String = .discover
	let justReleasedTitle: String = .justReleased
	let moreOfWhatYouLikeTitle: String = .moreOfWhatYouLike
	let newmArtistsTitle: String = .newmArtists
	let mostPopularThisWeekTitle: String = .mostPopularThisWeek
	let homeTitle: String = .home

	@Published var route: HomeRoute?
	
	@Injected private var getHomeViewUseCase: GetHomeViewUseCase

	@Published var state: ViewState<HomeViewUIModel> = .loading
	
	init() {
		refresh()
	}
	
	func refresh() {		
		state = .loaded(getHomeViewUseCase.execute())
	}
}

extension ThisWeekCellModel: Identifiable {
	var id: ObjectIdentifier { labelText.objectIdentifier }
}
