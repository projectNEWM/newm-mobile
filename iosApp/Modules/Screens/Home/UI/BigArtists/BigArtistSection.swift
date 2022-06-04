import SwiftUI

struct BigArtistSection: View {
	private let cellTitleFont: Font = .inter(ofSize: 12).bold()
	private let cellSubtitleFont: Font = .inter(ofSize: 12)
	private let cellSubtitleColor: Color = Color(.grey100)
	
	let artists: [HomeViewModel.BigArtist]
	let title: String

    var body: some View {
		LazyHStack(alignment: .top, spacing: 12) {
			ForEach(artists) { model in
				BigArtistCell(model: model)
			}
		}
		.addHorizontalScrollView(title: title)
    }
}

struct BigArtistSection_Previews: PreviewProvider {
    static var previews: some View {
		BigArtistSection(artists: MockData.moreOfWhatYouLikeCells, title: "MORE OF WHAT YOU LIKE")
			.preferredColorScheme(.dark)
    }
}
