import Foundation
import SwiftUI
import shared

public protocol YourPlaylistsViewProviding {
	func yourPlaylistsView(id: String) -> AnyView
}
