import Foundation
import SwiftUI
import shared

public protocol SongPlayingViewProviding {
	func songPlayingView(song: Song) -> AnyView
}
