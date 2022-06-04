import SwiftUI
import Resolver

struct CompactArtistsSection: View {
	let artists: [HomeViewModel.CompactArtist]
	let title: String
	
	var body: some View {
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
		CompactArtistsSection(artists: MockData.artistCells, title: "NEWM ARTISTS")
			.preferredColorScheme(.dark)
	}
}
