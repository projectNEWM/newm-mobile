import Foundation
import shared
import Utilities

public class MockConnectWalletUseCase: ConnectWalletUseCase {	
	private var walletConnections: [WalletConnection] = []
	
	public init() {}
	
	public func connect(walletConnectionId id: String) async throws {
		walletConnections.append(WalletConnection(id: id, createdAt: "", stakeAddress: ""))
		NotificationCenter.default.post(name: NSNotification.Name(rawValue: Notification().walletConnectionStateChanged), object: nil)
	}
	
	public func disconnect(walletConnectionId: String?) async throws {
		if let walletConnectionId {
			walletConnections.removeAll { $0.id == walletConnectionId }
		} else {
			walletConnections.removeAll()
		}
		NotificationCenter.default.post(name: NSNotification.Name(rawValue: Notification().walletConnectionStateChanged), object: nil)
	}
	
	public func getWalletConnections() async throws -> [WalletConnection] {
		walletConnections
	}
	
	public func hasWalletConnections() async throws -> KotlinBoolean {
		try! await KotlinBoolean(bool: getWalletConnections().isEmpty == false)
	}
}

extension MockConnectWalletUseCase {
	public func hasWalletConnectionsFlow() throws -> any Kotlinx_coroutines_coreFlow {
		fatalError()
	}
	
	public func getWalletConnectionsFlow() throws -> any Kotlinx_coroutines_coreFlow {
		fatalError()
	}
}
