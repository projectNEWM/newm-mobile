import SwiftUI
import ModuleLinker
import Resolver
import SwiftUINavigation
import Artist

struct HomeView: View {
	@InjectedObject private var viewModel: HomeViewModel
	private let cellTitleFont: Font = .inter(ofSize: 12).bold()
	private let cellSubtitleFont: Font = .inter(ofSize: 12)
	private let cellSubtitleColor: Color = Color(.grey100)

	public init() {}
	
	public var body: some View {
		NavigationView {
			ZStack {
//				links
				allViews
			}
			.navigationBarTitleDisplayMode(.inline)
		}
	}
	
	private var moreOfWhatYouLike: some View {
		ScrollView(.horizontal) {
			LazyHStack(alignment: .top) {
				ForEach(viewModel.moreOfWhatYouLikes) { model in
					MoreOfWhatYouLikeCell(
						model: model,
						titleFont: cellTitleFont,
						subtitleFont: cellSubtitleFont,
						subtitleColor: cellSubtitleColor
					)
					.cornerRadius(10)
				}
			}
		}
		.addSectionTitle(viewModel.moreOfWhatYouLikeTitle)
	}
	
	private var artists: some View {
		ScrollView(.horizontal) {
			LazyHGrid(rows: [
				GridItem(.fixed(80)),
				GridItem(.fixed(80)),
				GridItem(.fixed(80))
			]) {
				ForEach(viewModel.artists) { artist in
					BigArtistCell(model: artist, titleFont: cellTitleFont, subtitleFont: cellSubtitleFont, subtitleColor: cellSubtitleColor)
				}
			}
			.fixedSize()
		}
	}
	
//	private var links: some View {
//		func clearLinks(isActive: Bool) {
//			if isActive == false { viewModel.homeRoute = nil }
//		}
//		return ZStack {
//			NavigationLink(unwrapping: $viewModel.homeRoute,
//						   case: /HomeRoute.songPlaying,
//						   destination: { $songId in
//				songPlayingViewProvider.songPlayingView(id: songId)
//			}, onNavigate: clearLinks, label: {})
//
//			NavigationLink(unwrapping: $viewModel.homeRoute,
//						   case: /HomeRoute.artist,
//						   destination: { $artistId in
//				artistViewProvider.artistView(id: artistId)
//			}, onNavigate: clearLinks, label: {})
//
//			NavigationLink(unwrapping: $viewModel.homeRoute,
//						   case: /HomeRoute.playlist,
//						   destination: { $playlistId in
//				playlistViewProvider.playlistView(id: playlistId)
//			}, onNavigate: clearLinks, label: {})
//		}
//	}
	
	private var allViews: some View {
		ScrollView {
			moreOfWhatYouLike
			artists
		}
		
		
//		VStack {
//			ScrollView {
//				VStack {
//					HomeScrollingContentView<ArtistCell>(selectedDataModel: { viewModel.homeRoute = .artist(id: $0.artistID) },
//														 dataModels: viewModel.artists,
//														 title: viewModel.artistSectionTitle,
//														 spacing: 8)
////					HomeScrollingContentView<SongCell>(selectedDataModel: { viewModel.homeRoute = .songPlaying(id: $0.songID) },
////													   dataModels: viewModel.songs,
////													   title: viewModel.songsSectionTitle,
////													   spacing: 8)
////					HomeScrollingContentView<PlaylistCell>(selectedDataModel: { viewModel.homeRoute = .playlist(id: $0.playlistID) },
////														   dataModels: viewModel.playlists,
////														   title: viewModel.playlistsSectionTitle,
////														   spacing: 12)
//				}
//			}
//		}
//		.frame(maxHeight: .infinity, alignment: .top)
	}
}

private extension View {
	func sectionTitleFont() -> some View {
		font(.inter(ofSize: 12)).foregroundColor(Color(.grey100))
	}
	
	func addSectionTitle(_ title: String) -> some View {
		VStack(alignment: .leading) {
			Text(title).sectionTitleFont()
			self
		}
	}
}

struct HomeView_Previews: PreviewProvider {
	static var previews: some View {
		//is this doing anything?
//		Resolver.main = .mock
		return HomeView()
			.preferredColorScheme(.dark)
	}
}
