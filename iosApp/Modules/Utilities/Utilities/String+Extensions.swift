import Foundation

public extension String {
	var objectIdentifier: ObjectIdentifier { ObjectIdentifier(NSString(string: self)) }
}
