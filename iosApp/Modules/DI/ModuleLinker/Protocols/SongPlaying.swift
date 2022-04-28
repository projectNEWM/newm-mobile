import Foundation
import SwiftUI

public protocol SongPlayingViewProviding {
	func songPlayingView(id: String) -> AnyView
}
