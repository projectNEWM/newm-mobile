import Foundation
import Resolver
import ModuleLinker
import shared

public final class LibraryModule: Module {
	public static let shared = LibraryModule()
		
	public func registerAllServices() {
		Resolver.register {
			self as LibraryViewProviding
		}
		
		Resolver.register {
			WalletNFTSongsUseCaseProvider().get() as WalletNFTTracksUseCase
		}
    }
}

//#if DEBUG
extension LibraryModule {
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
		
		mockResolver.register {
			return MockConnectWalletUseCase() as ConnectWalletUseCase
			class MockConnectWalletUseCase: ConnectWalletUseCase {
				var _isConnected: Bool = false
				func connect(xpub: String) {
					ConnectWalletUseCaseProvider().get().connect(xpub: "xpub1j6l5sgu597d72mu6tnzmrlt3mfv8d8qru2ys5gy4hf09g2v97ct8gslwcvkjyd8jkpefj226ccyw6al76af5hcf328myun6pwjl7wcgshjjxl")
					_isConnected = true
				}
				
				func disconnect() {
					_isConnected = false
				}
				
				func isConnected() -> Bool {
					return _isConnected
				}
				
				func isConnectedFlow() -> Kotlinx_coroutines_coreFlow {
					fatalError()
				}
			}
		}
	}
}
//#endif
