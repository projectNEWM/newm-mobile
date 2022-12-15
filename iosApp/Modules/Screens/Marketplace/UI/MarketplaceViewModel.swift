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
	
	@Published var titleSection = MarketplaceTitleSectionModel(title: "MARKETPLACE", gradient: Gradients.marketplaceGradient)
	let allCategories: [Category] = Category.allCases
	@Published var selectedCategory: Category = Category.allCases[0]
	@Published var selectedGenre: Genre = Genre.companion.allCases[0]
	@Published var selectedTimespan: Timespan = .hours24
	@Published var selectedFilterCategory: NFTFilterCategories = .mostPopular
	@Published var searchTerm: String = ""
	
	let trendingSongs = MockData.songs
	let newSongsToday = MockData.songs.shuffled()
	let popularSongs = MockData.songs.shuffled()
	let bloomingArtists = MockData.artists.shuffled()
	let nftSongs = MockData.songs

	var trendingSongsCells: CellsSectionModel<SongCellModel> { CellsSectionModel(cells: trendingSongs.filter { $0.title.lowercased().hasPrefix(searchTerm.lowercased()) }.map(\.trendingCellModel), title: "TRENDING SONGS") }
	var newSongsTodayCells: CellsSectionModel<SongCellModel> { CellsSectionModel(cells: newSongsToday.filter { $0.title.lowercased().hasPrefix(searchTerm.lowercased()) }.map(\.trendingCellModel), title: "NEW SONGS TODAY") }
	var popularSongsCells: CellsSectionModel<SongCellModel> { CellsSectionModel(cells: popularSongs.filter { $0.title.lowercased().hasPrefix(searchTerm.lowercased()) }.map(\.trendingCellModel), title: "POPULAR \(selectedCategory.description.uppercased()) SONGS") }
	var bloomingArtistsCells: CellsSectionModel<ArtistCellModel> { CellsSectionModel(cells: bloomingArtists.filter { $0.name.lowercased().hasPrefix(searchTerm.lowercased()) }.map(ArtistCellModel.init), title: "\(selectedCategory.description.uppercased()) ARTISTS BLOOMING") }
	var nftSongsCells: [Song] { nftSongs.filter { $0.title.lowercased().hasPrefix(searchTerm.lowercased()) } }
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
