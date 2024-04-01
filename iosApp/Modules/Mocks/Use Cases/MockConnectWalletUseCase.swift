import Foundation
import shared
import Utilities

public class MockConnectWalletUseCase: ConnectWalletUseCase {
	@UserDefault(defaultValue: nil)
	private var xpub: String?
	
	public init() {}

	public func connect(xpub: String) {
		self.xpub = xpub
		NotificationCenter.default.post(name: NSNotification.Name(rawValue: Notification().walletConnectionStateChanged), object: nil)
	}

	public func disconnect() {
		xpub = nil
		NotificationCenter.default.post(name: NSNotification.Name(rawValue: Notification().walletConnectionStateChanged), object: nil)
	}

	public func isConnected() -> Bool {
		return xpub != nil
	}
	
	public func isConnectedFlow() -> any Kotlinx_coroutines_coreFlow {
		fatalError()
	}
}
