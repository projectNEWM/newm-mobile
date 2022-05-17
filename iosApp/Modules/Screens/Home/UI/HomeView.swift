import SwiftUI
import ModuleLinker
import Resolver
import SwiftUINavigation
import Artist

struct HomeView: View {
	@InjectedObject private var viewModel: HomeViewModel
	@Injected private var gradientProvider: HomeViewGradientProviding
	
	@Injected private var artistViewProvider: ArtistViewProviding
	@Injected private var songPlayingViewProvider: SongPlayingViewProviding
	@Injected private var playlistViewProvider: PlaylistViewProviding
	@Injected private var allPlaylistsViewProvider: PlaylistListViewProviding

	public init() {}
	
	public var body: some View {
		NavigationView {
			ZStack {
				links
				allViews
			}
			.navigationTitle(viewModel.title)
			.navigationBarTitleDisplayMode(.inline)
		}
	}
	
	private var gradientLine: some View {
		return LinearGradient(
			colors: gradientProvider.gradientColors,
			startPoint: .leading,
			endPoint: .trailing
		).frame(height: 1)
	}
	
	private var links: some View {
		func clearLinks(isActive: Bool) {
			if isActive == false { viewModel.homeRoute = nil }
		}
		return ZStack {
			NavigationLink(unwrapping: $viewModel.homeRoute,
						   case: /HomeRoute.songPlaying,
						   destination: { $songId in
				songPlayingViewProvider.songPlayingView(id: songId)
			}, onNavigate: clearLinks, label: {})
			
			NavigationLink(unwrapping: $viewModel.homeRoute,
						   case: /HomeRoute.artist,
						   destination: { $artistId in
				artistViewProvider.artistView(id: artistId)
			}, onNavigate: clearLinks, label: {})

			NavigationLink(unwrapping: $viewModel.homeRoute,
						   case: /HomeRoute.playlist,
						   destination: { $playlistId in
				playlistViewProvider.playlistView(id: playlistId)
			}, onNavigate: clearLinks, label: {})
		}
	}
	
	private var allViews: some View {
		VStack {
			gradientLine
			ScrollView {
				VStack {
					SectionSelectorView(selectedIndex: $viewModel.selectedSection, sectionTitles: viewModel.sectionTitles)
					HomeScrollingContentView<ArtistCell>(selectedDataModel: { viewModel.homeRoute = .artist(id: $0.artistID) },
														 dataModels: viewModel.artists,
														 title: viewModel.artistSectionTitle,
														 spacing: 8)
					HomeScrollingContentView<SongCell>(selectedDataModel: { viewModel.homeRoute = .songPlaying(id: $0.songID) },
													   dataModels: viewModel.songs,
													   title: viewModel.songsSectionTitle,
													   spacing: 8)
					HomeScrollingContentView<PlaylistCell>(selectedDataModel: { viewModel.homeRoute = .playlist(id: $0.playlistID) },
														   dataModels: viewModel.playlists,
														   title: viewModel.playlistsSectionTitle,
														   spacing: 12)
				}
			}
		}
		.frame(maxHeight: .infinity, alignment: .top)
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
