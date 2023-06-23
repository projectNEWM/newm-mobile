import Foundation

public extension String {
	var nilIfEmpty: String? {
		isEmpty ? nil : self
	}
}

public extension String? {
	var emptyIfNil: String {
		self ?? ""
	}
}
