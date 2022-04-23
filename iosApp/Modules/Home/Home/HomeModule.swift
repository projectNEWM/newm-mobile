import Foundation
import Resolver
import ModuleLinker
import SwiftUI

public final class HomeModule: ModuleProtocol {
	public static let shared = HomeModule()
	
	private init() {
		registerAllServices()
	}

	public func registerAllServices() {
		Resolver.register {
			self as HomeViewProviding
		}
	}
}

extension HomeModule: HomeViewProviding {
	public func homeView() -> AnyView {
		HomeView().erased
	}
}
