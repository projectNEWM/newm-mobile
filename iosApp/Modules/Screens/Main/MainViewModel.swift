import Foundation
import Combine

protocol MainViewModelProtocol {
	var selectedTab: MainViewModelTab { get }
}

class MainViewModel: ObservableObject, MainViewModelProtocol {
	@Published var selectedTab: MainViewModelTab = .home
}
