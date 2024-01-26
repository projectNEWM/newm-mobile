import Foundation
import shared
import Utilities

public extension KMMException {
	var newmError: NEWMError {
		NEWMError(errorDescription: message, underlyingError: self as? Error)
	}
}
