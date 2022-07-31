import Foundation
import shared

private let logger = Logger(className: "iOS App")
public func Log(_ message: String) {
	logger.log(msg: message)
}

public func LogError(_ message: String) {
	//TODO: IMPLEMENT THIS
	logger.log(msg: message)
}
