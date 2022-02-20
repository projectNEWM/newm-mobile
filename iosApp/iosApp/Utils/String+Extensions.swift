import Foundation

extension String {
	var objectIdentifier: ObjectIdentifier { ObjectIdentifier(NSString(string: self)) }
}
