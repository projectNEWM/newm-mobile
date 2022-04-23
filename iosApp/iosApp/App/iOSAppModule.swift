import Foundation
import Resolver
import ModuleLinker
import Main
import Wallet
import Home
import SharedUI
import SwiftUI

struct iOSAppModule: ModuleProtocol {
	static var shared = iOSAppModule()
	
	let modules: [ModuleProtocol] = {
		[
			WalletModule.shared,
			HomeModule.shared,
			SharedUIModule.shared,
			MainModule.shared
		]
	}()
	
	func registerAllServices() {
		modules.forEach { $0.registerAllServices() }
	}
}

extension Resolver: ResolverRegistering {
	public static func registerAllServices() {
		let modules: [ModuleProtocol] = {
			[
				WalletModule.shared,
				HomeModule.shared,
				SharedUIModule.shared,
				MainModule.shared
			]
		}()
		
		modules.forEach { $0.registerAllServices() }
	}
}
