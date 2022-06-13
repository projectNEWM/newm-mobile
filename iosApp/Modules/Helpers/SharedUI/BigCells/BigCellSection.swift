import SwiftUI

public struct BigCellSection: View {
	private let cellTitleFont: Font = .inter(ofSize: 12).bold()
	private let cellSubtitleFont: Font = .inter(ofSize: 12)
	private let cellSubtitleColor: Color = Color(.grey100)
	
	private let model: CellsSectionModel<BigCellViewModel>
		
	public init(_ model: CellsSectionModel<BigCellViewModel>) {
		self.model = model
	}

    public var body: some View {
		LazyHStack(alignment: .top, spacing: 12) {
			ForEach(model.cells) { model in
				BigArtistCell(model: model)
			}
		}
		.addHorizontalScrollView(title: model.title)
    }
}

struct BigCellSection_Previews: PreviewProvider {
    static var previews: some View {
		BigCellSection(CellsSectionModel(cells: MockData.bigArtistCells, title: "MORE OF WHAT YOU LIKE"))
			.preferredColorScheme(.dark)
    }
}
