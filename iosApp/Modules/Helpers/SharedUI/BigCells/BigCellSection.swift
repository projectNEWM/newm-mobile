import SwiftUI

public struct BigCellSection: View {
	private let cellTitleFont: Font = .inter(ofSize: 12).bold()
	private let cellSubtitleFont: Font = .inter(ofSize: 12)
	private let cellSubtitleColor: Color = Color(.grey100)
	
	public let cells: [BigCellViewModel]
	public let title: String
	
	public init(cells: [BigCellViewModel], title: String) {
		self.cells = cells
		self.title = title
	}

    public var body: some View {
		LazyHStack(alignment: .top, spacing: 12) {
			ForEach(cells) { model in
				BigArtistCell(model: model)
			}
		}
		.addHorizontalScrollView(title: title)
    }
}

struct BigCellSection_Previews: PreviewProvider {
    static var previews: some View {
		BigCellSection(cells: MockData.bigArtistCells, title: "MORE OF WHAT YOU LIKE")
			.preferredColorScheme(.dark)
    }
}
