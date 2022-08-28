import Foundation
import SwiftUI
import shared

public protocol PlaylistViewProviding {
	func playlistView(id: String) -> AnyView
}

public protocol PlaylistListViewProviding {
	func playlistListView() -> AnyView
}

public protocol PlaylistListUseCaseProtocol {
	func execute() -> [Playlist]
}
