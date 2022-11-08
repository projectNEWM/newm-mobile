import SwiftUI
import Colors

public struct BigCellSection: View {
	private let cellTitleFont: Font = .inter(ofSize: 12).bold()
	private let cellSubtitleFont: Font = .inter(ofSize: 12)
	private let cellSubtitleColor = NEWMColor.grey100.swiftUIColor
	
	private let model: CellsSectionModel<BigCellViewModel>
	
	private let actionHandler: (String) -> ()
		
	public init(_ model: CellsSectionModel<BigCellViewModel>, actionHandler: @escaping (String) -> ()) {
		self.model = model
		self.actionHandler = actionHandler
	}

    public var body: some View {
		LazyHStack(alignment: .top, spacing: 12) {
			ForEach(model.cells) { model in
				BigArtistCell(model: model)
					.onTapGesture { actionHandler(model.artistID) }
			}
		}
		.addHorizontalScrollView(title: model.title)
    }
}

struct BigCellSection_Previews: PreviewProvider {
    static var previews: some View {
		BigCellSection(CellsSectionModel(cells: MockData.bigArtistCells, title: "MORE OF WHAT YOU LIKE"), actionHandler: {_ in})
			.preferredColorScheme(.dark)
    }
}
