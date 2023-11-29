import Foundation
import Resolver

//#if DEBUG
public extension Resolver {
	static var mock = Resolver(child: Resolver.root)
}
//#endif
