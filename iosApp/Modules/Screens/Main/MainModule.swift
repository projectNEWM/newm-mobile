import Foundation
import Resolver
import SwiftUI
import ModuleLinker
import TabBar

public final class MainModule: ModuleProtocol {
	public static let shared = MainModule()
	
	@LazyInjected var homeViewProvider: HomeViewProviding
	@LazyInjected var walletViewProvider: WalletViewProviding

	public func registerAllServices() {
		//TODO: should this be a separate resolver?
		// Internal
		Resolver.register { [unowned self] in
			[
				TabViewProvider(image: Image(MainViewModelTab.home), tabName: MainViewModelTab.home.description) {
					self.homeViewProvider.homeView()
				},
				TabViewProvider(image: Image(MainViewModelTab.wallet), tabName: MainViewModelTab.wallet.description) {
					self.walletViewProvider.walletView()
				}
			]
		}
		
		// Public
		Resolver.register { self as MainViewProviding }
	}
}

extension MainModule: MainViewProviding {
	public func mainView() -> AnyView {
		MainView().erased
	}
}

#if DEBUG
extension MainModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
	}
}
#endif
