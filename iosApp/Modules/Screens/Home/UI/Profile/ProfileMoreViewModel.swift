import Foundation
import Domain
import Resolver

@MainActor
class ProfileMoreViewModel: ObservableObject {
	typealias Row = (title: String, url: String)
	typealias Section = (title: String, rows: [Row])
	
	private let logInUseCase = LoginUseCase.shared
	
	var sections: [Section] {
		[
			(title: .helpAndSupport, [
				(title: .faq, url: "http://google.com"),
				(title: .askTheCommunity, url: "http://google.com")
			]),
			(title: .companyRelated, [
				(title: .documents, url: "http://google.com"),
				(title: .privacyPolicy, url: "http://google.com"),
				(title: .termsAndConditions, url: "http://google.com")
			])
		]
	}
	
	func logOut() {
		logInUseCase.logOut()
	}
}
