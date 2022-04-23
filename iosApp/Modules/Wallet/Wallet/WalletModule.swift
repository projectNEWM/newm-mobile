import Foundation
import Resolver
import ModuleLinker
import SwiftUI
import SharedUI

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
