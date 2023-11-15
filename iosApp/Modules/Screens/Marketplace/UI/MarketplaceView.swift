import SwiftUI
import SharedUI
import Resolver
import Colors
import ModuleLinker
import AppAudioPlayer

struct MarketplaceView: View {
	@StateObject private var viewModel = MarketplaceViewModel()
	
	private let largerSongCellSize: CGFloat = 200
	private let smallerSongCellSize: CGFloat = 150
	
	var body: some View {
		if viewModel.isLoading {
			ProgressView()
		} else {
			ScrollView {
				LazyVStack(spacing: 24) {
					title
					searchField
					radioPicker
					categorySubSection
					filters
					nftSongs
				}
			}
		}
	}
	
	@ViewBuilder
	private var radioPicker: some View {
		RadioPicker(options: viewModel.allCategories,
					selectedOption: $viewModel.selectedCategory,
					RadioButtonType: RadioButton.self,
					gradient: Gradients.marketplaceGradient)
	}
	
	@ViewBuilder
	private var title: some View {
		TitleSection(title: viewModel.titleSection.title,
					 gradient: viewModel.titleSection.gradient)
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
		.addSidePadding()
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
		case .none:
			EmptyView()
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
			mostPopularFilter(genre: genre)
		case .none:
			EmptyView()
		}
	}
	
	@ViewBuilder
	private var newSongsFilter: some View {
		Filters(selectedOption1: $viewModel.selectedGenre,
				selectedOption2: $viewModel.selectedTimespan,
				allOptions1: viewModel.genres,
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
			ForEach(viewModel.nftSongs, id: \.song.id, content: NFTCell.init)
		}
		.addSidePadding()
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

struct NFTCell: View {
	@InjectedObject private var audioPlayer: AudioPlayerImpl
	let model: NFTCellModel
	
	var body: some View {
		HStack {
			Group {
				if audioPlayer.song == model.song {
					Image(systemName: "pause.fill")
				} else {
					Image(systemName: "play.fill")
				}
			}
			.frame(width: 15)
			.padding(.trailing, 6)
			.fixedSize()
			
			HStack {
				AsyncImage(url: URL(string: model.song.image)) { image in
					image.circleImage(size: 24)
				} placeholder: {
					Image.placeholder.circleImage(size: 24)
				}
				
				Text(model.song.title)
				Spacer()
				//TODO: fix
				Text(model.value)
			}
			.font(.inter(ofSize: 12))
			.padding(8)
			.background(NEWMColor.grey600())
			.cornerRadius(6)
		}
		.onTapGesture {
			audioPlayer.song = model.song
			audioPlayer.playbackInfo.isPlaying = true
		}
		.frame(height: 40)
	}
}

struct MarketplaceView_Previews: PreviewProvider {
	static var previews: some View {
		Group {
			MarketplaceView()
			ArtistCell(model: ArtistCellModel(artist: MockData.artists.first!)).border(.white)
				.frame(width: 200)
				.fixedSize()
			NFTCell(
				model: NFTCellModel(
					song: MockData.songs.first!,
					value: "Ɲ\(Int.random09).\(Int.random09)\(Int.random09)"
				)
			)
		}
		.preferredColorScheme(.dark)
	}
}
