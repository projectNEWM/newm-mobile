import SwiftUI
import SharedUI
import Resolver
import Colors
import ModuleLinker

struct MarketplaceView: View {
	@Injected private var viewModel: MarketplaceViewModel
	
    var body: some View {
		ScrollView {
			LazyVStack(spacing: 32) {
				TitleSection(model: viewModel.titleSection)
				RadioPicker(options: viewModel.allCategories)
				trendingSongs(viewModel.trendingSongs)
				newSongsToday(viewModel.newSongsToday)
			}
		}
    }
	
	private func trendingSongs(_ model: CellsSectionModel<SongCellModel>) -> some View {
		HorizontalStackSection(viewModel.trendingSongs) { model in
			SongCell(model: model).frame(width: 200)
		}
	}
	
	private func newSongsToday(_ model: CellsSectionModel<SongCellModel>) -> some View {
		HorizontalScroller(title: model.title) {
			LazyHGrid(
				rows: Array(repeating: GridItem(.fixed(275)), count: 2),
				alignment: .top,
				spacing: 12
			) {
				ForEach(model.cells) { cellModel in
					SongCell(model: cellModel)
						.frame(width: 180, alignment: .leading)
						.fixedSize()
				}
			}
		}
	}
}

struct MarketplaceView_Previews: PreviewProvider {
    static var previews: some View {
        MarketplaceView()
			.preferredColorScheme(.dark)
    }
}
