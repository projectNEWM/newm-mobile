import Foundation
import SharedUI
import ModuleLinker
import Colors
import shared

class MarketplaceViewModel: ObservableObject {
	@Published var titleSection = TitleSectionModel(title: "MARKETPLACE", gradientColors: ColorAsset.marketplaceGradient.map(\.color))
	@Published var selectedCategory: Category = Category.allCases.first!
	@Published var trendingSongs: CellsSectionModel<TrendingSongCellModel> = CellsSectionModel(cells: MockData.songs.map(\.trendingCellModel), title: "TRENDING SONGS")
	
	let allCategories: [Category] = Category.allCases
}

extension MarketplaceViewModel {
	enum Category: CaseIterable, Hashable, CustomStringConvertible {
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
		
		case trending
		case newSongs
		case genre(Genre)
	}
}
