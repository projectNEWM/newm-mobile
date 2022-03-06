import Foundation

@propertyWrapper
struct Localizable {
	let wrappedValue: String
	
	init(wrappedValue: String) {
		self.wrappedValue = NSLocalizedString(wrappedValue, comment: "")
	}
	
	static func localize(_ string: String) -> String {
		NSLocalizedString(string, comment: "")
	}
}
