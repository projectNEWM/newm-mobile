import Foundation
import SwiftUI

public protocol NowPlayingViewProviding {
	func nowPlayingView() -> AnyView
}

public protocol MiniNowPlayingViewProviding {
	func miniNowPlayingView() -> AnyView
}
