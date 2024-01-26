import Foundation
import ModuleLinker
import shared

public class MockUserDetailsUseCase: UserDetailsUseCase {
	private let mockUser: User
	public var errorToThrow: Error?
	public let fetchTimeout: UInt64
	
	public init(mockUser: User, errorToThrow: Error? = nil, fetchTimeout: UInt64) {
		self.mockUser = mockUser
		self.errorToThrow = errorToThrow
		self.fetchTimeout = fetchTimeout
	}
	
	public func fetchLoggedInUserDetails() async throws -> User {
		try await Task.sleep(nanoseconds: 1_000_000_000 * fetchTimeout)
		
		if let errorToThrow {
			throw errorToThrow
		}
		
		return mockUser
	}
	
	public func fetchLoggedInUserDetailsFlow() -> Kotlinx_coroutines_coreFlow {
		fatalError()
	}
}
