import Foundation

public protocol ErrorReporting {
	func logError(_ error: String)
	func logError(_ error: Error)
}
