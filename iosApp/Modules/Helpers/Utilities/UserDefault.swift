import Foundation

@propertyWrapper
public struct UserDefault<Value: Codable> {
	private let key: String
	private let defaultValue: Value
	private let userDefaults: UserDefaults
	
	public var wrappedValue: Value {
		get {
			do {
				let rawValue = userDefaults.object(forKey: key)
				let result = try rawValue as? Value ?? (rawValue as? Data).flatMap { try JSONDecoder().decode([String: Value].self, from: $0)[key] }
				return result ?? defaultValue
			} catch {
				Log("Unable to decode with error: \(error)")
				return defaultValue
			}
		}
		set {
			do {
				let value = try JSONEncoder().encode([key: newValue])
				userDefaults.set(value, forKey: key)
			} catch {
				Log("Unable to encode with error: \(error)")
			}
		}
	}
	
	public init(_ key: String, defaultValue: Value, userDefaults: UserDefaults = UserDefaults.standard) {
		self.key = key
		self.defaultValue = defaultValue
		self.userDefaults = userDefaults
	}
}
