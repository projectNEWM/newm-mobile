import Foundation
import SwiftUI
import shared

public protocol LikedSongsViewProviding {
	func likedSongsView(id: String) -> AnyView
}
