import Foundation
import Sentry
import ModuleLinker

class SentryErrorReporter: ErrorReporting {
	func logError(_ error: String) {
		SentrySDK.capture(error: error)
	}
	
	func logError(_ error: Error) {
		SentrySDK.capture(error: error)
	}
}

extension String: Error {}
