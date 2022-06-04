import SwiftUI
import Resolver

public struct CompactArtistsSection: View {
	private let artists: [CompactArtistViewModel]
	private let title: String
	
	public init(artists: [CompactArtistViewModel], title: String) {
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
				CompactArtistCell(model: artist)
					.frame(width: 180, alignment: .leading)
					.fixedSize()
			}
		}
		.addHorizontalScrollView(title: title)
	}
}

struct CompactArtistsSection_Previews: PreviewProvider {
	static var previews: some View {
		CompactArtistsSection(artists: MockData.compactArtistCells, title: "NEWM ARTISTS")
			.preferredColorScheme(.dark)
	}
}
