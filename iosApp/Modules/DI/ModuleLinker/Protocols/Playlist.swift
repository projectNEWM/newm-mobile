import Foundation
import SwiftUI

public protocol PlaylistViewProviding {
	func playlistView(id: String) -> AnyView
}

public protocol PlaylistListViewProviding {
	func playlistListView() -> AnyView
}

public protocol PlaylistListUseCaseProtocol {
	func execute() -> [Playlist]
}

public protocol Playlist {
	var image: UIImage { get }
	var title: String { get }
	var creator: String { get }
	var genre: String { get }
	var starCount: String { get }
	var playCount: String { get }
	var playlistID: String { get }
	var id: ObjectIdentifier { get }
}

public extension Playlist {
	var id: ObjectIdentifier { playlistID.objectIdentifier }
}
