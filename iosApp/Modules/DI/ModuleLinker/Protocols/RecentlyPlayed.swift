import Foundation
import SwiftUI
import shared

public protocol RecentlyPlayedViewProviding {
	func recentlyPlayedView(id: String) -> AnyView
}

