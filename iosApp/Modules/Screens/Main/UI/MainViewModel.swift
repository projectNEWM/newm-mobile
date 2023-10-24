import Foundation
import Combine
import ModuleLinker
import Resolver
import Data
import API
import shared

@MainActor
class MainViewModel: ObservableObject {
	@Published var selectedTab: MainViewModelTab = .home
	
	var shouldShowLogin: Bool {
		loginUseCase.userIsLoggedIn == false
	}
	
	@Injected private var loginUseCase: LoginUseCase
	
	private var cancelables = Set<AnyCancellable>()
}
