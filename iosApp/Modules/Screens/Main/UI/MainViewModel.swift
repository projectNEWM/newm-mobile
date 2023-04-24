import Foundation
import Combine
import ModuleLinker
import Resolver
import shared

@MainActor
class MainViewModel: ObservableObject {
	@Published var selectedTab: MainViewModelTab = .home
	
	//TODO: check for facebook login access token AccessToken.current
	@Published var shouldShowLogin: Bool = false
	
	private var cancelables = Set<AnyCancellable>()
	
	@Injected private var userSession: UserSession
	
	init() {
		shouldShowLogin = !userSession.isUserLoggedIn()
	}
}
