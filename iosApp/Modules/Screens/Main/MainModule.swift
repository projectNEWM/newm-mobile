import Foundation
import Resolver
import SwiftUI
import ModuleLinker
import TabBar
import shared

public final class MainModule: Module {
	public static let shared = MainModule()

	public func registerAllServices() {
		// Internal
		Resolver.register {
			[
//				TabViewProvider(image: Image(MainViewModelTab.home), tabName: MainViewModelTab.home.description) {
//					@Injected var homeViewProvider: HomeViewProviding
//					return Resolver.resolve(HomeViewProviding.self).homeView()
//				},
                TabViewProvider(image: Image(MainViewModelTab.library), tabName: MainViewModelTab.library.description) {
					@Injected var libraryViewProvider: LibraryViewProviding
					return libraryViewProvider.libraryView()
                },
//				TabViewProvider(image: Image(MainViewModelTab.wallet), tabName: MainViewModelTab.wallet.description) {
//					@Injected var walletViewProvider: WalletViewProviding
//					return walletViewProvider.walletView()
//				},
//				TabViewProvider(image: Image(MainViewModelTab.marketplace), tabName: MainViewModelTab.marketplace.description) {
//					@Injected var marketplaceViewProvider: MarketplaceViewProviding
//					return marketplaceViewProvider.marketplaceView()
//				}
			]
		}
		
		Resolver.register {
			UserSessionUseCaseProvider().get() as UserSessionUseCase
		}

		// Public
		Resolver.register {
			self as MainViewProviding
		}
	}
}

extension MainModule: MainViewProviding {
	public func mainView() -> AnyView {
		MainView().erased
	}
}

//#if DEBUG
extension MainModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
		mockResolver.register {
			return MockWalletNFTTracksUseCase() as WalletNFTTracksUseCase
			class MockWalletNFTTracksUseCase: WalletNFTTracksUseCase {
				func getAllNFTTracks(completionHandler: @escaping ([NFTTrack]?, Error?) -> Void) {
					completionHandler(NFTTrack.mockTracks, nil)
				}
				
				func getNFTTrack(id: String) -> NFTTrack? {
					NFTTrack.mockTracks.first { $0.id == id }!
				}
				
				func getAllNFTTracksFlow() -> Kotlinx_coroutines_coreFlow {
					fatalError()
				}

				func getWalletNFTs() async throws -> [NFTTrack] {
					NFTTrack.mockTracks
				}
			}
		}
	}
}
//#endif
