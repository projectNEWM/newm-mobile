import Foundation

public enum APIError: LocalizedError {
	case invalidResponse
	case httpError(statusCode: Int, cause: String?)
	
	public var errorDescription: String? {
		switch self {
		case .httpError(_, let cause):
			return "\(cause ?? "Unknown")"
		default:
			return "Error"
		}
	}
}
