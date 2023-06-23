import Foundation

protocol DataFetching {
	func data(for request: URLRequest) async throws -> (Data, URLResponse)
}

extension URLSession: DataFetching {
	func data(for request: URLRequest) async throws -> (Data, URLResponse) {
		try await data(for: request, delegate: nil)
	}
}
