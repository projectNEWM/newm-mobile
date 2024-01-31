import Foundation
import ModuleLinker
import shared

public class MockUserDetailsUseCase: UserDetailsUseCase {
	private let mockUser: User
	public var errorToThrow: Error?
	public let fetchLatency: Double
	
	public init(mockUser: User = UserMocksKt.mockUsers.first!, errorToThrow: Error? = nil, fetchLatency: Double = 0.1) {
		self.mockUser = mockUser
		self.errorToThrow = errorToThrow
		self.fetchLatency = fetchLatency
	}
	
	public func fetchLoggedInUserDetails() async throws -> User {
		try await Task.sleep(for: .seconds(fetchLatency))
		
		if let errorToThrow {
			throw errorToThrow
		}
		
		return mockUser
	}
	
	public func fetchLoggedInUserDetailsFlow() -> Kotlinx_coroutines_coreFlow {
		fatalError()
	}
}
