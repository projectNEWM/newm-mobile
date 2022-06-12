import SwiftUI

public struct RecentlyPlayedArtistsSection: View {
	private let model: RecentlyPlayedArtistsSectionModel
	
	public init(_ model: RecentlyPlayedArtistsSectionModel) {
		self.model = model
	}
	
	public var body: some View {
		BigCellSection(cells: model.cellModels, title: model.title)
    }
}

struct RecentlyPlayedArtistsSection_Previews: PreviewProvider {
    static var previews: some View {
		RecentlyPlayedArtistsSection(RecentlyPlayedArtistsSectionModel(cellModels: MockData.bigArtistCells))
			.preferredColorScheme(.dark)
    }
}
