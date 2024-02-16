import Foundation
import ModuleLinker
import Resolver

public enum EnvironmentVariable: String {
	case sentryKey
	case recaptchaKey
	
	public var rawValue: String {
		value(forEnvVar: self)
	}
	
	private func value(forEnvVar envVar: EnvironmentVariable) -> String {
		if let value = ProcessInfo.processInfo.environment[envVar.rawValue] {
			return value
		} else {
			Resolver.resolve(ErrorReporting.self).logError("Env var not found: \(envVar.rawValue)")
			return ""
		}
	}
}
