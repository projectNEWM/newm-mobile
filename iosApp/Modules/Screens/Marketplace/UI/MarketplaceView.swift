import SwiftUI
import SharedUI
import Resolver
import Colors
import ModuleLinker

struct MarketplaceView: View {
	@InjectedObject private var viewModel: MarketplaceViewModel
	
	private let largerSongCellSize: CGFloat = 200
	private let smallerSongCellSize: CGFloat = 150
	
	var body: some View {
		ScrollView {
			LazyVStack(spacing: 32) {
				TitleSection(model: viewModel.titleSection)
				RadioPicker(options: viewModel.allCategories, selectedOption: $viewModel.selectedCategory)
				categorySubSection
			}
		}
	}
	
	@ViewBuilder
	private var categorySubSection: some View {
		switch viewModel.selectedCategory {
		case .trending:
			HorizontalStackSection(viewModel.trendingSongs) { model in
				SongCell(model: model).frame(width: largerSongCellSize)
			}
		case .newSongs:
			horizontalSmallSongScroller(viewModel.newSongsToday, rows: 2)
		case .genre:
			horizontalArtistScroller(viewModel.bloomingArtists, rows: 2)
			horizontalSmallSongScroller(viewModel.popularSongs, rows: 1)
		}
	}
}

extension MarketplaceView {
	private func horizontalArtistScroller(_ model: CellsSectionModel<ArtistCellModel>, rows: Int) -> some View {
		HorizontalScroller(title: model.title) {
			LazyHGrid(
				rows: Array(repeating: GridItem(.fixed(155)), count: rows),
				alignment: .top,
				spacing: 20
			) {
				ForEach(model.cells) { cellModel in
					ArtistCell(model: cellModel)
						.fixedSize()
				}
			}.padding(.top, 5)
		}
	}

	private func horizontalSmallSongScroller(_ model: CellsSectionModel<SongCellModel>, rows: Int) -> some View {
		HorizontalScroller(title: model.title) {
			LazyHGrid(
				rows: Array(repeating: GridItem(.fixed(250)), count: rows),
				alignment: .top,
				spacing: 12
			) {
				ForEach(model.cells) { cellModel in
					SongCell(model: cellModel)
						.frame(width: smallerSongCellSize, alignment: .leading)
						.fixedSize()
				}
			}
		}
	}
}

struct ArtistCell: View {
	let model: ArtistCellModel
	
	var body: some View {
		VStack(spacing: 4) {
			AsyncImage(url: URL(string: model.imageUrl)) { image in
				image.circleImage(size: 100)
			} placeholder: {
				Image.placeholder
			}
			Text(model.name)
				.font(.inter(ofSize: 12).bold())
				.padding(.top, 8)
			//TODO: localize
			Text("\(model.songsCount) songs")
				.font(.inter(ofSize: 12))
				.foregroundColor(NEWMColor.grey100.swiftUIColor)
		}
		.lineLimit(1)
		.frame(width: 100)
	}
}

struct MarketplaceView_Previews: PreviewProvider {
    static var previews: some View {
		Group {
			MarketplaceView()
			ArtistCell(model: ArtistCellModel(artist: MockData.artists.first!)).border(.white)
				.frame(width: 200)
				.fixedSize()
		}
		.preferredColorScheme(.dark)
    }
}