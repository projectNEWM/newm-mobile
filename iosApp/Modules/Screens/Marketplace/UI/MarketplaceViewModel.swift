import Foundation
import SharedUI
import ModuleLinker
import Colors
import shared

class MarketplaceViewModel: ObservableObject {
	enum Timespan: CaseIterable, CustomStringConvertible {
		case hours24
		case days7
		case days30
		
		var description: String {
			switch self {
			case .days30: return "30 days"
			case .days7: return "7 days"
			case .hours24: return "24 hours"
			}
		}
	}
	
	enum NFTFilterCategories: CaseIterable, CustomStringConvertible {
		case mostPopular
		case biggestBooty
		case longestTrack
		
		var description: String {
			switch self {
			case .biggestBooty: return "Biggest Booty"
			case .longestTrack: return "Longest Track"
			case .mostPopular: return "Most Popular"
			}
		}
	}
	
	@Published var titleSection = TitleSectionModel(title: "MARKETPLACE", gradientColors: ColorAsset.marketplaceGradient.map(\.color))
	let allCategories: [Category] = Category.allCases
	@Published var selectedCategory: Category = Category.allCases[2]
	@Published var selectedGenre: Genre = Genre.companion.allCases[0]
	@Published var selectedTimespan: Timespan = .hours24
	@Published var selectedFilterCategory: NFTFilterCategories = .mostPopular

	lazy var trendingSongs: CellsSectionModel<SongCellModel> = CellsSectionModel(cells: MockData.songs.map(\.trendingCellModel), title: "TRENDING SONGS")
	lazy var newSongsToday: CellsSectionModel<SongCellModel> = CellsSectionModel(cells: MockData.songs.shuffled().map(\.trendingCellModel), title: "NEW SONGS TODAY")
	lazy var popularSongs: CellsSectionModel<SongCellModel> = CellsSectionModel(cells: MockData.songs.shuffled().map(\.trendingCellModel), title: "POPULAR \(selectedCategory.description.uppercased()) SONGS")
	lazy var bloomingArtists: CellsSectionModel<ArtistCellModel> = CellsSectionModel(cells: MockData.artists.shuffled().map(ArtistCellModel.init), title: "\(selectedCategory.description.uppercased()) ARTISTS BLOOMING")
	lazy var nftSongs: [Song] = MockData.songs
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

extension Song: Identifiable {}
