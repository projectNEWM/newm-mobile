import XCTest
@testable import API
import Models

let expiredRefreshJwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiJkOWJkYTg5OC01NTc1LTQzMDctYTc5Ni00ZWM4Yzg1ZjBkMmQiLCJpc3MiOiJodHRwczovL25ld20uaW8iLCJhdWQiOiJuZXdtLXNlcnZlci11c2VycyIsInN1YiI6ImQ0MmNiMWU4LWFiMzItNGUwZi1hOGRkLWRhNTFlZmRlNGUwYiIsImV4cCI6MTAsInR5cGUiOiJSZWZyZXNoIiwiYWRtaW4iOmZhbHNlfQ.YW1YlsZ-fqis3ZU3AUeHHXmUkD9LkVCGZlrY63vWxGA"
let unexpiredRefreshJwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiJkOWJkYTg5OC01NTc1LTQzMDctYTc5Ni00ZWM4Yzg1ZjBkMmQiLCJpc3MiOiJodHRwczovL25ld20uaW8iLCJhdWQiOiJuZXdtLXNlcnZlci11c2VycyIsInN1YiI6ImQ0MmNiMWU4LWFiMzItNGUwZi1hOGRkLWRhNTFlZmRlNGUwYiIsImV4cCI6OTk5OTk5OTk5OTk5LCJ0eXBlIjoiUmVmcmVzaCIsImFkbWluIjpmYWxzZX0.oJrmneGx-j9jOGGuOXg4cxHBUbpIpYmjrS-PmDxOpHA"
let expiredAccessJwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiJjMDdiNGM1NS1iYzlhLTRkZjItYjNhZi05N2I4MzFjYjE4NzIiLCJpc3MiOiJodHRwczovL25ld20uaW8iLCJhdWQiOiJuZXdtLXNlcnZlci11c2VycyIsInN1YiI6ImQ0MmNiMWU4LWFiMzItNGUwZi1hOGRkLWRhNTFlZmRlNGUwYiIsImV4cCI6MTAsInR5cGUiOiJBY2Nlc3MiLCJhZG1pbiI6ZmFsc2V9.HTNSPXHPVPss5iyRIEbhgS-l9amoWuGO3J0FQasN94c"
let unexpiredAccessJwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiJjMDdiNGM1NS1iYzlhLTRkZjItYjNhZi05N2I4MzFjYjE4NzIiLCJpc3MiOiJodHRwczovL25ld20uaW8iLCJhdWQiOiJuZXdtLXNlcnZlci11c2VycyIsInN1YiI6ImQ0MmNiMWU4LWFiMzItNGUwZi1hOGRkLWRhNTFlZmRlNGUwYiIsImV4cCI6OTk5OTk5OTk5OTk5LCJ0eXBlIjoiQWNjZXNzIiwiYWRtaW4iOmZhbHNlfQ.lo72bxGhQPNyIGgiUK2nUtOU4QyKByTTLhIkWA97c-Q"

let accessTokenKey = "testAccessToken"
let refreshTokenKey = "testRefreshToken"

final class APITests: XCTestCase {
	let tokenManager = AuthTokenManager(accessTokenKey: accessTokenKey, refreshTokenKey: refreshTokenKey)
	
	override func setUp() async throws {
		try await super.setUp()
		tokenManager.authToken = nil
	}
	
	override func tearDown() async throws {
		try await super.tearDown()
		tokenManager.authToken = nil
	}
	
	func testLogin() async throws {
		class MockDataFetcher: DataFetching {
			func data(for request: URLRequest) async throws -> (Data, URLResponse) {
				let token = AuthToken(accessToken: unexpiredAccessJwt, refreshToken: unexpiredRefreshJwt)
				return (
					try JSONEncoder().encode(token),
					HTTPURLResponse(url: URL(string: "http://google.com")!, statusCode: 200, httpVersion: nil, headerFields: nil)!
				)
			}
		}
		
		let loginAPI = LoginAPI(tokenManager: tokenManager, dataFetcher: MockDataFetcher())
		XCTAssertFalse(loginAPI.userIsLoggedIn)
		try await loginAPI.login(method: .apple(idToken: "asldfkj"))
		XCTAssertTrue(loginAPI.userIsLoggedIn)
		loginAPI.logOut()
		XCTAssertFalse(loginAPI.userIsLoggedIn)
	}
	
	func testGetCurrentUserAndTokenRefresh() async throws {
		class MockDataFetcher: DataFetching {
			func data(for request: URLRequest) async throws -> (Data, URLResponse) {
				let encodable: Encodable
				if request.url?.pathComponents.contains("refresh") == true {
					encodable = AuthToken(accessToken: unexpiredAccessJwt, refreshToken: unexpiredRefreshJwt)
				} else if request.url?.pathComponents.contains("me") == true {
					XCTAssertNotNil(request.value(forHTTPHeaderField: "Authorization")?.range(of: unexpiredAccessJwt))
					encodable = User(id: "1", createdAt: "", firstName: "", lastName: "", nickname: "", pictureUrl: URL(string: "http://")!, bannerUrl: URL(string: "http://")!, email: "http://")
				} else {
					fatalError()
				}
				
				return (
					try JSONEncoder().encode(encodable),
					HTTPURLResponse(url: request.url!, statusCode: 200, httpVersion: nil, headerFields: nil)!
				)
			}
		}
		
		tokenManager.authToken = AuthToken(accessToken: expiredAccessJwt, refreshToken: unexpiredRefreshJwt)
		XCTAssert(tokenManager.authToken!.accessTokenExpired)
		XCTAssertFalse(tokenManager.authToken!.refreshTokenExpired)
		
		let api = UserAPI(tokenManager: tokenManager, dataFetcher: MockDataFetcher())
		XCTAssertTrue(api.userIsLoggedIn)
		let _ = try await api.getCurrentUser()
		XCTAssertFalse(tokenManager.authToken!.accessTokenExpired)
		XCTAssertFalse(tokenManager.authToken!.refreshTokenExpired)
		XCTAssertTrue(api.userIsLoggedIn)
	}
	
	func testVerificationCodeRequest() async throws {
		class MockDataFetcher: DataFetching {
			var isValid: Bool
			let email: String
			
			init(isValid: Bool = false, email: String) {
				self.isValid = isValid
				self.email = email
			}
			
			func data(for request: URLRequest) -> (Data, URLResponse) {
				if
					request.url?.query()?.contains(email) == true,
					request.url?.pathComponents.contains("code") == true
				{
					isValid = true
				}
				return (Data(), HTTPURLResponse())
			}
		}
		
		tokenManager.authToken = AuthToken(accessToken: unexpiredAccessJwt, refreshToken: unexpiredRefreshJwt)
		let email = "hi@you.com"
		let dataFetcher = MockDataFetcher(email: email)
		let api = LoginAPI(tokenManager: tokenManager, dataFetcher: dataFetcher)
		
		try await api.requestEmailVerificationCode(for: email)
		XCTAssertTrue(dataFetcher.isValid)
	}
}
