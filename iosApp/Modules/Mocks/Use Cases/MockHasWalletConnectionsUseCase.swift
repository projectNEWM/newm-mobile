import Foundation
import shared

public class MockHasWalletConnectionsUseCase: HasWalletConnectionsUseCase {
	var hasWalletConnections: Bool = false
	
	public init() {}
	
	public func hasWalletConnections() async throws -> KotlinBoolean {
		KotlinBoolean(bool: hasWalletConnections)
	}
	
	public func hasWalletConnectionsFlow() throws -> any Kotlinx_coroutines_coreFlow {
		fatalError()
	}
}
