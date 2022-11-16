import Foundation
import SwiftUI

public protocol NowPlayingViewProviding {
	func nowPlayingView() -> AnyView
}

public protocol MinimizedNowPlayingViewProviding {
	func minimizedNowPlayingView() -> AnyView
}
