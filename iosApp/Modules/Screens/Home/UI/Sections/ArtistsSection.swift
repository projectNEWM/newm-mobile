import SwiftUI
import Resolver
import SharedUI

public struct HorizontalScrollingGridView: View {
	private let model: CellsSectionModel<CompactCellModel>
	private let actionHandler: (String) -> ()
	
	public init(_ model: CellsSectionModel<CompactCellModel>, actionHandler: @escaping (String) -> ()) {
		self.model = model
		self.actionHandler = actionHandler
	}
	
	public var body: some View {
		HorizontalScroller(title: model.title) {
			LazyHGrid(rows: [
				GridItem(.fixed(60)),
				GridItem(.fixed(60)),
				GridItem(.fixed(60))
			], alignment: .top, spacing: 50) {
				ForEach(model.cells) { cellModel in
					CompactCell(model: cellModel, roundImage: true)
						.frame(width: 180, alignment: .leading)
						.fixedSize()
						.onTapGesture { actionHandler(cellModel.modelID) }
				}
			}
		}
	}
}

struct HorizontalScrollingGridView_Previews: PreviewProvider {
	static var previews: some View {
		HorizontalScrollingGridView(CellsSectionModel(cells: SharedUI.MockData.compactArtistCells, title: "NEWM ARTISTS"), actionHandler: {_ in})
			.preferredColorScheme(.dark)
	}
}
