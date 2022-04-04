import Foundation
import Strings

extension String {
	@Localizable static var newm = "NEWM"
}

class ModuleClass {}

@propertyWrapper
struct Localizable {
	let wrappedValue: String
	
	init(wrappedValue: String) {
		self.wrappedValue = Strings.Localizable.localize(wrappedValue, bundle: Bundle(for: ModuleClass.self))
	}
	
	static func localize(_ string: String, bundle: Bundle) -> String {
		NSLocalizedString(string, tableName: nil, bundle: bundle, value: "", comment: "")
	}
}

