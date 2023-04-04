import Foundation
import SwiftUI
import shared

public protocol MarketplaceViewProviding {
	func marketplaceView() -> AnyView
}

public struct SongCellModel: Identifiable {
	public let song: Song
	public var id: String { song.id }
	public var imageUrl: String { song.image }
	public var title: String { song.title }
	public var artistUrl: String { song.artist.image }
	public var artistName: String { song.artist.name}
	public var nftPrice: String = "Ɲ 2.1"
	
	public init(song: Song) {
		self.song = song
	}
}

public extension Song {
	var trendingCellModel: SongCellModel {
		SongCellModel(song: self)
	}
}
