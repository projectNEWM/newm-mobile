import Foundation
import Data
import Resolver
import shared

@MainActor
class ProfileMoreViewModel: ObservableObject {
	typealias Row = (title: String, url: String)
	typealias Section = (title: String, rows: [Row])
	
	@Injected private var loginUseCase: LoginUseCase
	
	var sections: [Section] {
		[
			(title: .helpAndSupport, [
				(title: .faq, url: "https://www.youtube.com/watch?v=LBjUh4bYF8w"),
				(title: .askTheCommunity, url: "https://www.youtube.com/watch?v=dQw4w9WgXcQ")
			]),
			(title: .companyRelated, [
				(title: .documents, url: "https://www.youtube.com/watch?v=dQw4w9WgXcQ"),
				(title: .privacyPolicy, url: "https://www.youtube.com/watch?v=LBjUh4bYF8w"),
				(title: .termsAndConditions, url: "https://www.youtube.com/watch?v=dQw4w9WgXcQ")
			])
		]
	}
	
	func logOut() {
		loginUseCase.logOut()
	}
}
