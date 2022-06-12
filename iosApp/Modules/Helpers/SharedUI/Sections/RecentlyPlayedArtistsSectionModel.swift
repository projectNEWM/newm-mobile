import Foundation
import Utilities

public struct RecentlyPlayedArtistsSectionModel {
	public init(cellModels: [BigCellViewModel]) {
		self.cellModels = cellModels
	}
	
	public let cellModels: [BigCellViewModel]
	public let title: String = .recentlyPlayed
}
