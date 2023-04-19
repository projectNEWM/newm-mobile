import Foundation
import SharedUI
import ModuleLinker
import Colors
import shared
import Resolver

@MainActor
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
		case biggestTrack
		case longestTrack
		
		var description: String {
			switch self {
			case .biggestTrack: return "Biggest Track"
			case .longestTrack: return "Longest Track"
			case .mostPopular: return "Most Popular"
			}
		}
	}
	
	var genres: [String] = []
	
	//TODO: for some reason if I use @Injected here, it tries to get called before it's registered.  wtf.
	private var getGenresUseCase: GetGenresUseCase!
	
	@Published var isLoading: Bool = true
	@Published var error: String? = nil
	
	@Published var titleSection = MarketplaceTitleSectionModel(title: "MARKETPLACE", gradient: Gradients.marketplaceGradient)
	var allCategories: [Category] { Category.allCases(genres: genres) }
	@Published var selectedCategory: Category!
	@Published var selectedGenre: String!
	@Published var selectedTimespan: Timespan = .hours24
	@Published var selectedFilterCategory: NFTFilterCategories = .mostPopular
	@Published var searchTerm: String = ""
	
	let trendingSongs = MockData.songs
	let newSongsToday = MockData.songs.shuffled()
	let popularSongs = MockData.songs.shuffled()
	let bloomingArtists = MockData.artists.shuffled()
	let nftSongs = MockData.songs.map { NFTCellModel(song: $0, value: "Ɲ\(Int.random09).\(Int.random09)\(Int.random09)") }

	var trendingSongsCells: CellsSectionModel<SongCellModel> { CellsSectionModel(cells: trendingSongs.filter { $0.title.lowercased().hasPrefix(searchTerm.lowercased()) }.map(\.trendingCellModel), title: "TRENDING SONGS") }
	var newSongsTodayCells: CellsSectionModel<SongCellModel> { CellsSectionModel(cells: newSongsToday.filter { $0.title.lowercased().hasPrefix(searchTerm.lowercased()) }.map(\.trendingCellModel), title: "NEW SONGS TODAY") }
	var popularSongsCells: CellsSectionModel<SongCellModel> { CellsSectionModel(cells: popularSongs.filter { $0.title.lowercased().hasPrefix(searchTerm.lowercased()) }.map(\.trendingCellModel), title: "POPULAR \(selectedCategory.description.uppercased()) SONGS") }
	var bloomingArtistsCells: CellsSectionModel<ArtistCellModel> { CellsSectionModel(cells: bloomingArtists.filter { $0.name.lowercased().hasPrefix(searchTerm.lowercased()) }.map(ArtistCellModel.init), title: "\(selectedCategory.description.uppercased()) ARTISTS BLOOMING") }
	var nftSongsCells: [NFTCellModel] { nftSongs.filter { $0.song.title.lowercased().hasPrefix(searchTerm.lowercased()) } }
	
	init() {
		isLoading = true
		do {
			getGenresUseCase = try GetGenresUseCaseFactory().getGenresUseCase()
		} catch {
			self.error = error.localizedDescription
		}
		Task {
			await fetch()
			selectedCategory = allCategories[0]
			selectedGenre = genres[0]
			isLoading = false
		}
	}
	
	private func fetch() async {
		isLoading = true
		do {
			genres = try await getGenresUseCase.getGenres()
		} catch {
			self.error = error.localizedDescription
		}
		isLoading = false
	}
}

struct ArtistCellModel: Identifiable {
	let artist: Artist
	
	var imageUrl: String { artist.image }
	var name: String { artist.name }
	var songsCount: Int { Int.random(in: 1..<100) }
	var id: String { artist.id }
}

extension MarketplaceViewModel {
	enum Category: Hashable, CustomStringConvertible {
		case trending
		case newSongs
		case genre(String)

		//TODO: localize
		var description: String {
			switch self {
			case .newSongs:
				return "New Songs"
			case .trending:
				return "Trending"
			case .genre(let genre):
				return genre
			}
		}

		static func allCases(genres: [String]) -> [Category] {
			[Category.trending, Category.newSongs]
			+
			genres.map(Category.genre)
		}
	}
}

extension Song: Identifiable {}

extension Int {
	static var random09: Int {
		Int.random(in: 0..<9)
	}
}

struct NFTCellModel {
	let song: Song
	let value: String
}
