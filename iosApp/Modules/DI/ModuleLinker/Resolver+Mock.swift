import Foundation
import Resolver

#if DEBUG
public extension Resolver {
	static var mock = Resolver()
}
#endif
