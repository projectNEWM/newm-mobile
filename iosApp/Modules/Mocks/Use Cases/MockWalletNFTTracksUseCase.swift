import Foundation
import shared

class MockWalletNFTTracksUseCase: WalletNFTTracksUseCase {
	var walletSynced: any Kotlinx_coroutines_coreFlow { fatalError() }
	
	func getAllTracks() async throws -> [NFTTrack] {
		[]
	}
		
	func getAllTracksFlow() async throws -> any Kotlinx_coroutines_coreFlow {
		fatalError()
	}
	
	func getAllCollectableTracks() async throws -> [NFTTrack] {
		[]
	}
	
	func getAllCollectableTracksFlow() -> any Kotlinx_coroutines_coreFlow {
		fatalError()
	}
	
	func getAllNFTTracks() async throws -> [NFTTrack] {
		NFTTrack.mocks
	}
	
	func getAllNFTTracksFlow() -> any Kotlinx_coroutines_coreFlow {
		fatalError()
	}
	
	func getAllStreamTokens() async throws -> [NFTTrack] {
		NFTTrack.mocks
	}
	
	func getAllStreamTokensFlow() -> any Kotlinx_coroutines_coreFlow {
		fatalError()
	}
	
	func getNFTTrack(id: String) throws -> NFTTrack {
		NFTTrack.mocks.first { $0.id == id }!
	}
	
	func refresh() async throws {
		// no-op
	}
}
