import shared

class MockSyncWalletConnectionsUseCase: SyncWalletConnectionsUseCase {
	var numberOfConnections: Int = 5
	func syncWalletConnectionsFromNetworkToDevice() async throws -> [WalletConnection] {
		WalletConnection.mocks(numberOfConnections: numberOfConnections)
	}
}
