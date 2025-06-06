import shared

class MockGetWalletConnectionsUseCase: GetWalletConnectionsUseCase {
	func getWalletConnectionsFromCache() async throws -> [WalletConnection] {
		WalletConnection.mocks(numberOfConnections: 5)
	}
	
	func getWalletConnectionsFromCacheFlow() throws -> any Kotlinx_coroutines_coreFlow {
		fatalError()
	}
}
