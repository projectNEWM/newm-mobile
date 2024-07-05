import Foundation
import Resolver

public extension Resolver {
	static var mock: Resolver { Resolver(child: .main) }
}
