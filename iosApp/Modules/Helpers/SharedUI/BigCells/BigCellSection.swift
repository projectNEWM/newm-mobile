//import SwiftUI
//import Colors
//
//public struct HorizontalScroller: View {
//    public var body: some View {
//		LazyHStack(alignment: .top, spacing: 12) {
//			ForEach(model.cells) { model in
//				BigArtistCell(model: model)
//					.onTapGesture { actionHandler(model.artistID) }
//			}
//		}
//		.addHorizontalScrollView(title: model.title)
//    }
//}
//
//struct BigCellSection_Previews: PreviewProvider {
//    static var previews: some View {
//		BigCellSection(CellsSectionModel(cells: MockData.bigArtistCells, title: "MORE OF WHAT YOU LIKE"), actionHandler: {_ in})
//			.preferredColorScheme(.dark)
//    }
//}
