import Foundation
import Combine
import ModuleLinker
import Resolver
import shared
import Auth

@MainActor
class MainViewModel: ObservableObject {
	@Published var selectedTab: MainViewModelTab = .home
	
	@Published var shouldShowLogin: Bool = false
	
	private let loginManager = LoginManager()
	
	private var cancelables = Set<AnyCancellable>()
	
	init() {
		loginManager.$userIsLoggedIn.map { !$0 }.assign(to: &$shouldShowLogin)
	}
}
