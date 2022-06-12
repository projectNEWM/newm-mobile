import SwiftUI
import Resolver
import SharedUI

public struct ArtistsSection: View {
	private let artists: [CompactCellViewModel]
	private let title: String
	
	public init(artists: [CompactCellViewModel], title: String) {
		self.artists = artists
		self.title = title
	}
	
	public var body: some View {
		LazyHGrid(rows: [
			GridItem(.fixed(60)),
			GridItem(.fixed(60)),
			GridItem(.fixed(60))
		], alignment: .top, spacing: 50) {
			ForEach(artists) { artist in
				CompactCell(model: artist, roundImage: true)
					.frame(width: 180, alignment: .leading)
					.fixedSize()
			}
		}
		.addHorizontalScrollView(title: title)
	}
}

struct CompactArtistsSection_Previews: PreviewProvider {
	static var previews: some View {
		ArtistsSection(artists: SharedUI.MockData.compactArtistCells, title: "NEWM ARTISTS")
			.preferredColorScheme(.dark)
	}
}
