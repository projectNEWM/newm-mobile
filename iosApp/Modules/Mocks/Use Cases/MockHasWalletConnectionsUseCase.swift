import Foundation
import shared

class MockHasWalletConnectionsUseCase: HasWalletConnectionsUseCase {
	var hasWalletConnections: Bool = false
	func hasWalletConnections() async throws -> KotlinBoolean {
		KotlinBoolean(bool: hasWalletConnections)
	}
	
	func hasWalletConnectionsFlow() throws -> any Kotlinx_coroutines_coreFlow {
		fatalError()
	}
}
