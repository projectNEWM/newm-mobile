import SwiftUI
import Resolver

struct ArtistsSection: View {
	let artists: [HomeViewModel.Artist]
	let title: String
	
	var body: some View {
		LazyHGrid(rows: [
			GridItem(.fixed(50)),
			GridItem(.fixed(50)),
			GridItem(.fixed(50))
		], alignment: .top, spacing: 50) {
			ForEach(artists) { artist in
				BigArtistCell(model: artist)
					.frame(width: 180, alignment: .leading)
					.fixedSize()
			}
		}
		.addHorizontalScrollView(title: title)
	}
}

struct ArtistsSection_Previews: PreviewProvider {
	static var previews: some View {
		ArtistsSection(artists: MockData.artistCells, title: "NEWM ARTISTS")
			.preferredColorScheme(.dark)
	}
}
