import Foundation
import ModuleLinker
import Resolver
import SwiftUI

public final class TipsModule: ModuleProtocol {
	public static var shared = TipsModule()
	
	public func registerAllServices() {
		Resolver.register {
			self as TipViewProviding
		}
	}
	
#if DEBUG
	public func registerAllMockedServices(mockResolver: Resolver) {
		
	}
#endif
}

extension TipsModule: TipViewProviding {
	public func tipView(tipSelected: @escaping (TipAmount) -> ()) -> AnyView {
		TipView(tipSelected: tipSelected).erased
	}
}
