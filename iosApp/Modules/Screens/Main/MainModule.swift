import Foundation
import Resolver
import SwiftUI
import ModuleLinker
import TabBar
import shared

class MockUserSession: UserSession {
	var userLoggedIn = true
	
	func isUserLoggedIn() -> Bool {
		userLoggedIn
	}
}

public final class MainModule: ModuleProtocol {
	public static let shared = MainModule()

	public func registerAllServices() {
		// Internal
		Resolver.register {
			[
				TabViewProvider(image: Image(MainViewModelTab.home), tabName: MainViewModelTab.home.description) {
					@Injected var homeViewProvider: HomeViewProviding
					return Resolver.resolve(HomeViewProviding.self).homeView()
				},
                TabViewProvider(image: Image(MainViewModelTab.library), tabName: MainViewModelTab.library.description) {
					@Injected var libraryViewProvider: LibraryViewProviding
					return libraryViewProvider.libraryView()
                },
				TabViewProvider(image: Image(MainViewModelTab.wallet), tabName: MainViewModelTab.wallet.description) {
					@Injected var walletViewProvider: WalletViewProviding
					return walletViewProvider.walletView()
				},
//				TabViewProvider(image: Image(MainViewModelTab.marketplace), tabName: MainViewModelTab.marketplace.description) {
//					@Injected var marketplaceViewProvider: MarketplaceViewProviding
//					return marketplaceViewProvider.marketplaceView()
//				}
			]
		}
		
		Resolver.register {
			do {
				return try UserSessionFactory().userSession() as UserSession
			} catch {
				print(error)
				fatalError(error.localizedDescription)
			}
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
