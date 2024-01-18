import Foundation

public struct NEWMError: LocalizedError {
	public var errorDescription: String?
	public var failureReason: String?
	public var helpAnchor: String?
	public var recoverySuggestion: String?
	
	public init(errorDescription: String? = nil, failureReason: String? = nil, helpAnchor: String? = nil, recoverySuggestion: String? = nil) {
		self.errorDescription = errorDescription
		self.failureReason = failureReason
		self.helpAnchor = helpAnchor
		self.recoverySuggestion = recoverySuggestion
	}
}

public extension Error {
	var newmError: NEWMError {
		NEWMError(errorDescription: localizedDescription)
	}
}

public extension String {
	var newmError: NEWMError {
		NEWMError(errorDescription: self)
	}
}

public struct ErrorSet {
	private var errors: [NEWMError] = []
	
	public var currentError: NEWMError? {
		errors.first
	}
	
	public init() {}
		
	mutating
	public func append(error: NEWMError) {
		errors.append(error)
	}
	
	mutating
	public func popFirstError() {
		errors.removeFirst()
	}
}
