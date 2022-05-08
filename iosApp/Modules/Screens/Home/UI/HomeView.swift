import SwiftUI
import ModuleLinker
import Resolver

struct HomeView: View {
	@InjectedObject private var viewModel: HomeViewModel
	@Injected private var gradientProvider: HomeViewGradientProviding
	@Injected private var songPlayingViewProvider: SongPlayingViewProviding
	@Injected private var playlistViewProvider: PlaylistViewProviding
	@Injected private var allPlaylistsViewProvider: PlaylistListViewProviding

	public init() {}
	
	public var body: some View {
		NavigationView {
			ZStack {
//				links
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
	
//	private var links: some View {
//		ZStack {
//			switch viewModel.selection {
//			case .song(let id):
//				NavigationLink(tag: HomeViewModel., selection: $viewModel.selection, destination: { songPlayingViewProvider.songPlayingView(id: "Song1") }, label: {})
//			case .playlist(let id):
//				NavigationLink(tag: HomeViewModel.Selection.playlist(id: id), selection: $viewModel.selection, destination: { playlistViewProvider.playlistView(id: id) }, label: {})
//			case .allPlaylists:
//				NavigationLink(tag: HomeViewModel.Selection.allPlaylists, selection: $viewModel.selection, destination: { allPlaylistsViewProvider.playlistListView() }, label: {})
//			default:
//				EmptyView()
//			}
//		}
//	}
	
	private var allViews: some View {
		VStack {
			gradientLine
			ScrollView {
				VStack {
//					SectionSelectorView(selectedIndex: $viewModel.selectedSection, sectionTitles: viewModel.sectionTitles)
//					HomeScrollingContentView<ArtistCell>(selectedDataModel: { viewModel.navigation.selectedArtistID = $0.artistID },
//														 dataModels: viewModel.artists,
//														 title: viewModel.artistSectionTitle,
//														 spacing: 8)
//					HomeScrollingContentView<SongCell>(selectedDataModel: { viewModel.navigation.selectedSongID = $0.songID },
//													   dataModels: viewModel.songs,
//													   title: viewModel.songsSectionTitle,
//													   spacing: 8)
//					HomeScrollingContentView<PlaylistCell>(selectedDataModel: { viewModel.navigation.selectedPlaylistID = $0.playlistID },
//														   dataModels: viewModel.playlists,
//														   title: viewModel.playlistsSectionTitle,
//														   spacing: 12)
				}
			}
		}
		.frame(maxHeight: .infinity, alignment: .top)
	}
}

struct HomeView_Previews: PreviewProvider {
	static var previews: some View {
		return HomeView()
			.preferredColorScheme(.dark)
	}
}
