import Foundation

@propertyWrapper
public struct Localizable {
	public let wrappedValue: String
	
	public init(wrappedValue: String, _ bundleClass: AnyClass) {
		self.wrappedValue = NSLocalizedString(wrappedValue.description, tableName: nil, bundle: Bundle(for: bundleClass), value: "", comment: "")
	}
	
	public static func localize(_ string: String, bundle: Bundle) -> String {
		NSLocalizedString(string, tableName: nil, bundle: bundle, value: "", comment: "")
	}
}
