import SwiftUI
import Resolver
import SharedUI

public struct HorizontalScrollingGridView: View {
	private let model: CellsSectionModel<BigCellViewModel>
	
	public init(_ model: CellsSectionModel<BigCellViewModel>) {
		self.model = model
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
						.onTapGesture(perform: cellModel.onTap)
				}
			}
		}
	}
}

struct HorizontalScrollingGridView_Previews: PreviewProvider {
	static var previews: some View {
		HorizontalScrollingGridView(CellsSectionModel(cells: MockData.bigArtistCells_shuffled(seed: 1, onTap: {_ in}), title: "NEWM ARTISTS"))
			.preferredColorScheme(.dark)
	}
}
