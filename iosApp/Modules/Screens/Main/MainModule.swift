import Foundation
import Resolver
import SwiftUI
import ModuleLinker
import shared

public final class MainModule: Module {
	public static let shared = MainModule()

	public func registerAllServices() {
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

#if DEBUG
extension MainModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
		mockResolver.register {
			return MockWalletNFTTracksUseCase() as WalletNFTTracksUseCase
			class MockWalletNFTTracksUseCase: WalletNFTTracksUseCase {
				func getAllStreamTokensFlow() -> Kotlinx_coroutines_coreFlow {
					fatalError()
				}
				
				func getAllStreamTokens() async throws -> [NFTTrack] {
					NFTTrackMocksKt.mockTracks
				}
				
				func getAllStreamTokens() -> Kotlinx_coroutines_coreFlow {
					fatalError()
				}
				
				func getNFTTrack(id: String) -> NFTTrack? {
					NFTTrackMocksKt.mockTracks.first { $0.id == id }
				}
				
				func getAllNFTTracks() async throws -> [NFTTrack] {
					NFTTrackMocksKt.mockTracks
				}
				
				func getAllNFTTracksFlow() -> Kotlinx_coroutines_coreFlow {
					fatalError()
				}
			}
		}
	}
}
#endif
