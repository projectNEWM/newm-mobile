import Foundation
import ModuleLinker
import Resolver

@propertyWrapper
public struct UserDefault<Value: Codable> {
	private let key: String
	private let defaultValue: Value
	private let userDefaults: UserDefaults
	@Injected private var logger: ErrorReporting
	
	public var wrappedValue: Value {
		get {
			do {
				let rawValue = userDefaults.object(forKey: key)
				let result = try rawValue as? Value ?? (rawValue as? Data).flatMap { try JSONDecoder().decode([String: Value].self, from: $0)[key] }
				return result ?? defaultValue
			} catch {
				logger.logError("Unable to decode with error: \(error)")
				return defaultValue
			}
		}
		set {
			do {
				let value = try JSONEncoder().encode([key: newValue])
				userDefaults.set(value, forKey: key)
			} catch {
				logger.logError("Unable to encode with error: \(error)")
			}
		}
	}
	
	public init(_ key: String? = nil, defaultValue: Value, userDefaults: UserDefaults = UserDefaults.standard) {
		self.key = key ?? "\(Value.self)"
		self.defaultValue = defaultValue
		self.userDefaults = userDefaults
	}
}
