import SwiftUI
import SharedUI
import Resolver
import Colors
import ModuleLinker
import shared

struct MarketplaceView: View {
	@InjectedObject private var viewModel: MarketplaceViewModel
	
	private let largerSongCellSize: CGFloat = 200
	private let smallerSongCellSize: CGFloat = 150
	
	var body: some View {
		ScrollView {
			LazyVStack(spacing: 24) {
				TitleSection(model: viewModel.titleSection)
				searchField
				RadioPicker(options: viewModel.allCategories, selectedOption: $viewModel.selectedCategory)
				categorySubSection
				filters
				nftSongs
			}
		}
	}
	
	@ViewBuilder
	private var searchField: some View {
		HStack {
			Image(systemName: "magnifyingglass")
			TextField("Search songs", text: $viewModel.searchTerm)
				.padding(8)
				.background(NEWMColor.grey500())
				.overlay(RoundedRectangle(cornerRadius: 4)
					.stroke(NEWMColor.grey400(), lineWidth: 2))
				.font(.interMedium(ofSize: 16))
		}
		.padding([.leading, .trailing], sidePadding)
	}
	
	@ViewBuilder
	private var categorySubSection: some View {
		switch viewModel.selectedCategory {
		case .trending:
			if viewModel.trendingSongsCells.cells.count == 0 {
				Text("No results").padding().addSectionTitle(viewModel.trendingSongsCells.title)
			} else {
				HorizontalStackSection(viewModel.trendingSongsCells) { model in
					SongCell(model: model).frame(width: largerSongCellSize)
				}
			}
		case .newSongs:
			if viewModel.newSongsTodayCells.cells.count == 0 {
				Text("No results").padding().addSectionTitle(viewModel.newSongsTodayCells.title)
			} else {
				horizontalSmallSongScroller(viewModel.newSongsTodayCells, rows: 2)
			}
		case .genre:
			if viewModel.bloomingArtistsCells.cells.isEmpty {
				Text("No results").padding().addSectionTitle(viewModel.bloomingArtistsCells.title)
			} else {
				horizontalArtistScroller(viewModel.bloomingArtistsCells, rows: 2)
			}
			
			if viewModel.popularSongsCells.cells.isEmpty {
				Text("No results").padding().addSectionTitle(viewModel.popularSongsCells.title)
			} else {
				horizontalSmallSongScroller(viewModel.popularSongsCells, rows: 1)
			}
		}
	}
	
	@ViewBuilder
	private var filters: some View {
		switch viewModel.selectedCategory {
		case .newSongs:
			newSongsFilter.padding(.top, -30)
		case .trending:
			mostPopularFilter()
		case .genre(let genre):
			mostPopularFilter(genre: genre.title)
		}
	}
	
	@ViewBuilder
	private var newSongsFilter: some View {
		Filters(selectedOption1: $viewModel.selectedGenre,
				selectedOption2: $viewModel.selectedTimespan,
				allOptions1: Genre.companion.allCases,
				allOptions2: MarketplaceViewModel.Timespan.allCases,
				middlePrompt: "songs in the last",
				showNew: true)
	}
	
	@ViewBuilder
	private func mostPopularFilter(genre: String? = nil) -> some View {
		Filters(selectedOption1: $viewModel.selectedFilterCategory,
				selectedOption2: $viewModel.selectedTimespan,
				allOptions1: MarketplaceViewModel.NFTFilterCategories.allCases,
				allOptions2: MarketplaceViewModel.Timespan.allCases,
				middlePrompt: "\(genre ?? "") songs in the last",
				showNew: false)
	}
	
	@ViewBuilder
	private var nftSongs: some View {
		LazyVStack {
			ForEach(viewModel.nftSongs, content: NFTCell.init)
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
