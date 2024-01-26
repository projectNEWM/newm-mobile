import Foundation
import shared

public extension Error {
	var kmmException: KMMException? { (self as NSError).kotlinException as? KMMException }
}
