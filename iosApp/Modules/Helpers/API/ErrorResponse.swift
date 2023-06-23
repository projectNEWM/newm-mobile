import Foundation

struct ErrorResponse: Decodable {
	let code: Int
	let description: String
	let cause: String
}
