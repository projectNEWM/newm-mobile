import Foundation
import SwiftUI
import shared

//TODO: rename this to NowPlayingViewProviding and use AudioPlayer for its data source (for Song)
public protocol NowPlayingViewProviding {
	func nowPlayingView() -> AnyView
}

public protocol MinimizedNowPlayingViewProviding {
	func minimizedNowPlayingView() -> AnyView
}
