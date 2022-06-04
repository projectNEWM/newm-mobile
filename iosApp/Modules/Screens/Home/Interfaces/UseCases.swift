import Foundation
import shared

public protocol GetArtistsUseCase {
	func execute() -> [Artist]
}

public protocol GetMostPopularThisWeekUseCase {
	func execute() -> [Artist]
}

public protocol GetMoreOfWhatYouLikeUseCase {
	func execute() -> [Artist]
}
