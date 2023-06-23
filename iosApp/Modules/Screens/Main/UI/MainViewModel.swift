import Foundation
import Combine
import ModuleLinker
import Resolver
import Data
import API

@MainActor
class MainViewModel: ObservableObject {
	@Published var selectedTab: MainViewModelTab = .home
	
	@Published var shouldShowLogin: Bool = false
	
	private let loginRepo = LoginRepo.shared
	
	private var cancelables = Set<AnyCancellable>()
	
	init() {
		loginRepo.$userIsLoggedIn.map { !$0 }.assign(to: &$shouldShowLogin)
	}
}
