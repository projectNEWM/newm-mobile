import Foundation
import Resolver
import ModuleLinker
import SwiftUI

public struct WalletModule: ModuleProtocol {
	public static let shared = WalletModule()

	public func registerAllServices() {
		Resolver.register { self as WalletViewProviding }
	}
}

extension WalletModule: WalletViewProviding {
	public func walletView() -> AnyView {
		WalletView().erased
	}
}

#if DEBUG
extension WalletModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
	}
}
#endif
