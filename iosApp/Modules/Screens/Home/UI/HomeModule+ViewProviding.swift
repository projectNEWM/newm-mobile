import Foundation
import SwiftUI
import ModuleLinker

extension HomeModule: HomeViewProviding {
	public func homeView() -> AnyView {
		HomeView().erased
	}
}
