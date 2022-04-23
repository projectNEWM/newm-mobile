import Foundation
import Combine
import Strings
import Login
import Utilities

protocol MainViewModelProtocol {
	var selectedTab: MainViewModelTab { get }
}

class MainViewModel: ObservableObject, MainViewModelProtocol {
	@Published var selectedTab: MainViewModelTab = .home
}
