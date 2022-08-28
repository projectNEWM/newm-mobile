import Foundation
import SwiftUI
import Resolver

public protocol ModuleProtocol {
	static var shared: Self { get }
	func registerAllServices()
#if DEBUG
	func registerAllMockedServices(mockResolver: Resolver)
#endif
}
