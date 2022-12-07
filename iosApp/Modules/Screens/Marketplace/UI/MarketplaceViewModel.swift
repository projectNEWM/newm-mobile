import Foundation
import SharedUI
import ModuleLinker
import Colors
import shared

class MarketplaceViewModel: ObservableObject {
	@Published var titleSection = TitleSectionModel(title: "MARKETPLACE", gradientColors: ColorAsset.marketplaceGradient.map(\.color))
	let allCategories: [Category] = Category.allCases
	@Published var selectedCategory: Category = Category.allCases[2]
	
	lazy var trendingSongs: CellsSectionModel<SongCellModel> = CellsSectionModel(cells: MockData.songs.map(\.trendingCellModel), title: "TRENDING SONGS")
	lazy var newSongsToday: CellsSectionModel<SongCellModel> = CellsSectionModel(cells: MockData.songs.shuffled().map(\.trendingCellModel), title: "NEW SONGS TODAY")
	lazy var popularSongs: CellsSectionModel<SongCellModel> = CellsSectionModel(cells: MockData.songs.shuffled().map(\.trendingCellModel), title: "POPULAR \(selectedCategory.description.uppercased()) SONGS")
	lazy var bloomingArtists: CellsSectionModel<ArtistCellModel> = CellsSectionModel(cells: MockData.artists.shuffled().map(ArtistCellModel.init), title: "\(selectedCategory.description.uppercased()) ARTISTS BLOOMING")
}

struct ArtistCellModel: Identifiable {
	let artist: Artist
	
	var imageUrl: String { artist.image }
	var name: String { artist.name }
	var songsCount: Int { Int.random(in: 1..<100) }
	var id: String { artist.id }
}

extension MarketplaceViewModel {
	enum Category: CaseIterable, Hashable, CustomStringConvertible {
		case trending
		case newSongs
		case genre(Genre)
		
		//TODO: localize
		var description: String {
			switch self {
			case .newSongs:
				return "New Songs"
			case .trending:
				return "Trending"
			case .genre(let genre):
				return genre.title
			}
		}
		
		static var allCases: [MarketplaceViewModel.Category] {
			[.trending, .newSongs]
			+
			Genre.Companion().allCases.map(Category.genre)
		}
	}
}
