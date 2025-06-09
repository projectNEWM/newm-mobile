import Foundation
import ModuleLinker
import Resolver

public enum EnvironmentVariable: String {
	case recaptchaKey = "RECAPTCHA_KEY_ID"

	public var value: String {
		if let value = ProcessInfo.processInfo.environment[rawValue] {
			return value
		} else {
			Resolver.resolve(ErrorReporting.self).logError("Config var not found: \(rawValue)")
			return ""
		}
	}
}
