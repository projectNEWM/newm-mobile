import Foundation
import ModuleLinker

extension String {
	@Localizable static var newm = "NEWM"
}

@propertyWrapper
struct Localizable {
	let wrappedValue: String
	
	init(wrappedValue: String) {
		self.wrappedValue = Localizable.localize(wrappedValue, bundle: Bundle(for: TipsModule.self))
	}
	
	static func localize(_ string: String, bundle: Bundle) -> String {
		NSLocalizedString(string, tableName: nil, bundle: bundle, value: "", comment: "")
	}
}

