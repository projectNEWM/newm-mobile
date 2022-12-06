import Foundation
import SwiftUI
import shared

public protocol MarketplaceViewProviding {
	func marketplaceView() -> AnyView
}

public struct TrendingSongCellModel: Identifiable {
	public let id: String
	public let imageUrl: String
	public let title: String
	public let artistUrl: String
	public let artistName: String
	public let nftPrice: String = "Ɲ 2.1"
	
	public init(id: String, imageUrl: String, title: String, artistUrl: String, artistName: String) {
		self.id = id
		self.imageUrl = imageUrl
		self.title = title
		self.artistUrl = artistUrl
		self.artistName = artistName
	}
}

public extension Song {
	var trendingCellModel: TrendingSongCellModel {
		TrendingSongCellModel(
			id: id,
			imageUrl: image,
			title: title,
			artistUrl: artist.image,
			artistName: artist.name
		)
	}
}
