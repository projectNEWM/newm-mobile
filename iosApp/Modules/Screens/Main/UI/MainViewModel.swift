import Foundation
import Combine
import ModuleLinker
import Resolver

class MainViewModel: ObservableObject {
	private var cancellables = Set<AnyCancellable>()

	@Published var selectedTab: MainViewModelTab = .home
	@MainActor @Published var shouldShowLogin: Bool = false

	@Injected private var loggedInUserUseCase: LoggedInUserUseCaseProtocol
	
	init() {
//		loggedInUserUseCase.loggedInUser
//			.map { $0 == nil }
//			.assign(to: \.shouldShowLogin, on: self)
//			.store(in: &cancellables)
	}
}
