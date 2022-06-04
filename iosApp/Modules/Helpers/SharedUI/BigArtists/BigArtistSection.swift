import SwiftUI

public struct BigArtistSection: View {
	private let cellTitleFont: Font = .inter(ofSize: 12).bold()
	private let cellSubtitleFont: Font = .inter(ofSize: 12)
	private let cellSubtitleColor: Color = Color(.grey100)
	
	public let artists: [BigArtistViewModel]
	public let title: String
	
	public init(artists: [BigArtistViewModel], title: String) {
		self.artists = artists
		self.title = title
	}

    public var body: some View {
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
		BigArtistSection(artists: MockData.bigArtistCells, title: "MORE OF WHAT YOU LIKE")
			.preferredColorScheme(.dark)
    }
}
