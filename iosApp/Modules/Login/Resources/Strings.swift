import Foundation
import Strings

enum Strings: String, CustomStringConvertible {
	var description: String { rawValue }
	
	case title = "ENTER_NEWMIVERSE"
	case emailPlaceholder = "YOUR_EMAIL"
	case passwordPlaceholder = "PASSWORD"
	case forgotPassword = "FORGOT_PASSWORD"
	case enterNewm = "ENTER_NEWM"
	case createAccount = "CREATE_FREE_ACCOUNT"
}

class ModuleClass {}

@propertyWrapper
struct Localizable {
	let wrappedValue: String
	
	init(wrappedValue: String) {
		self.wrappedValue = NSLocalizedString(wrappedValue.description, tableName: nil, bundle: Bundle(for: ModuleClass.self), value: "", comment: "")
	}
	
	init(wrappedValue: Strings) {
		self.wrappedValue = NSLocalizedString(wrappedValue.description, tableName: nil, bundle: Bundle(for: ModuleClass.self), value: "", comment: "")
	}

	static func localize(_ string: String, bundle: Bundle) -> String {
		NSLocalizedString(string, tableName: nil, bundle: bundle, value: "", comment: "")
	}
}

