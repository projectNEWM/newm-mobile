import SwiftUI
import Resolver
import SharedUI
import shared

public struct TrackSection: View {
	@State var range: Range<Int> = 0..<4
	
	private let model: CellsSectionModel<BigCellViewModel>
	
	public init(_ model: CellsSectionModel<BigCellViewModel>) {
		self.model = model
	}
	
	public var body: some View {
		LazyVStack(alignment: .leading) {
			ForEach(model.cells) { cellModel in
				CompactCell(model: cellModel, roundImage: false)
					.frame(maxWidth: .infinity, alignment: .leading)
					.onTapGesture(perform: cellModel.onTap)
			}
		}
		.addSidePadding()
		.addSectionTitle(model.title)
	}
}

struct TrackSection_Previews: PreviewProvider {
	static var previews: some View {
		ScrollView {
			TrackSection(CellsSectionModel(cells: MockData.bigSongCells_shuffled(seed: 1) {_ in}, title: "TOP SONGS"))
		}
		.preferredColorScheme(.dark)
	}
}
