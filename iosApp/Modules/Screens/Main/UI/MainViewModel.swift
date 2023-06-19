import Foundation
import Combine
import ModuleLinker
import Resolver
import Domain
import API

@MainActor
class MainViewModel: ObservableObject {
	@Published var selectedTab: MainViewModelTab = .home
	
	@Published var shouldShowLogin: Bool = false
	
	private let logInUseCase = LoginUseCase.shared
	
	private var cancelables = Set<AnyCancellable>()
	
	init() {
		logInUseCase.$userIsLoggedIn.map { !$0 }.assign(to: &$shouldShowLogin)
	}
}
