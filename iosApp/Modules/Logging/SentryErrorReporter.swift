import Foundation
import Sentry
import ModuleLinker
import shared

class SentryErrorReporter: ErrorReporting {
	func logError(_ error: String) {
		SentrySDK.capture(error: error.kmmException ?? error)
	}
	
	func logError(_ error: Error) {
		SentrySDK.capture(error: error.kmmException ?? error)
	}
}

extension String: Error {}
extension KMMException: Error {}
