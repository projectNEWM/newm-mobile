#if DEBUG
import Foundation
import SwiftUI
import ModuleLinker
import Resolver
import shared
import SharedUI

class MockGetArtistsUseCase: GetArtistsUseCase {
	func execute() -> [Artist] {
		MockData.artists
	}
}

class MockGetMostPopularThisWeekUseCase: GetMostPopularThisWeekUseCase {
	func execute() -> [Artist] {
		MockData.artists
	}
}

class MockGetMoreOfWhatYouLikeUseCase: GetMoreOfWhatYouLikeUseCase {
	func execute() -> [Artist] {
		MockData.artists
	}
}
#endif
