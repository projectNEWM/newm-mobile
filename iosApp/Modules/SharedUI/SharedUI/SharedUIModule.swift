import Foundation
import Resolver
import ModuleLinker
import SwiftUI

public struct SharedUIModule: ModuleProtocol {
	public static let shared = SharedUIModule()
	
	public func registerAllServices() {
		Resolver.register {
			self as GradientTagProviding
		}
	}
}

extension SharedUIModule: GradientTagProviding {
	public func gradientTag(title: String) -> AnyView {
		GradientTag(title: title).erased
	}
}
