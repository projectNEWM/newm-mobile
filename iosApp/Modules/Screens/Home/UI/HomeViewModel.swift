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
	let recentlyPlayedTitle: String = .recentlyPlayed
	let justReleasedTitle: String = .justReleased
	let moreOfWhatYouLikeTitle: String = .moreOfWhatYouLike
	let newmArtistsTitle: String = .newmArtists
	let mostPopularThisWeekTitle: String = .mostPopularThisWeek
	let homeTitle: String = .home

	@Published var route: HomeRoute?
	
	@Injected private var getThisWeekSectionUseCase: GetHomeViewUseCase

	@Published var state: ViewState<HomeViewUIModel> = .loading
	
	init() {
		refresh()
	}
	
	func refresh() {		
		state = .loaded(getThisWeekSectionUseCase.execute())
	}
}

extension ThisWeekCellModel: Identifiable {
	var id: ObjectIdentifier { labelText.objectIdentifier }
}
