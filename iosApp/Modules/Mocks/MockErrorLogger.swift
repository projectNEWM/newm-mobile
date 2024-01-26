import Foundation
import ModuleLinker

public class MockErrorLogger: ErrorReporting {
	public var errorsLogged = [Error]()
	
	public init() {}
	
	public func logError(_ error: String) {
		errorsLogged.append(error)
	}
	
	public func logError(_ error: Error) {
		errorsLogged.append(error)
	}
}

extension String: Error {}
