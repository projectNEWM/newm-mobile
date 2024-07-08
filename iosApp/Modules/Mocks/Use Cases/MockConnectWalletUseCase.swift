import Foundation
import shared
import Utilities

public class MockConnectWalletUseCase: ConnectWalletUseCase {
	public func connect(walletConnectionId: String) async throws -> WalletConnection? {
		if let throwThisError {
			throw throwThisError
		}
		
		walletConnections.append(WalletConnection(id: walletConnectionId, createdAt: "", stakeAddress: ""))
		NotificationCenter.default.post(name: NSNotification.Name(rawValue: Notification().walletConnectionStateChanged), object: nil)
		
		return WalletConnection(id: "", createdAt: "", stakeAddress: "")
	}
	
	private var walletConnections: [WalletConnection] = []
	
	public var throwThisError: Error?
	
	public init() {}

	public func disconnect(walletConnectionId: String?) async throws {
		if let throwThisError {
			throw throwThisError
		}
		
		if let walletConnectionId {
			walletConnections.removeAll { $0.id == walletConnectionId }
		} else {
			walletConnections.removeAll()
		}
		NotificationCenter.default.post(name: NSNotification.Name(rawValue: Notification().walletConnectionStateChanged), object: nil)
	}
	
	public func getWalletConnections() async throws -> [WalletConnection] {
		if let throwThisError {
			throw throwThisError
		}

		return walletConnections
	}
	
	public func hasWalletConnections() async throws -> KotlinBoolean {
		if let throwThisError {
			throw throwThisError
		}

		return try! await KotlinBoolean(bool: getWalletConnections().isEmpty == false)
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
