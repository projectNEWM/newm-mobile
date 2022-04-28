import SwiftUI
import ModuleLinker
import Resolver

struct HomeView: View {
	@InjectedObject private var viewModel: HomeViewModel
	@Injected private var gradientProvider: HomeViewGradientProviding
	@Injected private var idLinker: IDLinking
	@Injected private var songPlayingViewProvider: SongPlayingViewProviding
	
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
		ZStack {
//			IDLink<ArtistView>(selectedID: viewModel.selectedArtist?.artistID)
//			idLinker.idLink(selectedID: viewModel.selectedSong?.songID, linkedView: <#T##_.Type#>)
//			idLinker.idLink(selectedID: viewModel.selectedPlaylist?.playlistID)
		}
	}
	
	private var allViews: some View {
		VStack {
			gradientLine
			ScrollView {
				VStack {
					SectionSelectorView(selectedIndex: $viewModel.selectedSectionIndex, sectionTitles: viewModel.sections)
					HomeScrollingContentView<ArtistCell>(selectedDataModel: $viewModel.selectedArtist, dataModels: viewModel.artists, title: viewModel.artistSectionTitle, spacing: 8)
					HomeScrollingContentView<SongCell>(selectedDataModel: $viewModel.selectedSong, dataModels: viewModel.songs, title: viewModel.songsSectionTitle, spacing: 8)
					HomeScrollingContentView<PlaylistCell>(selectedDataModel: $viewModel.selectedPlaylist, dataModels: viewModel.playlists, title: viewModel.playlistsSectionTitle, spacing: 12)
				}
			}
		}
		.frame(maxHeight: .infinity, alignment: .top)
		.onAppear { viewModel.deselectAll() }
	}
}

struct HomeView_Previews: PreviewProvider {
	static var previews: some View {
		return HomeView()
			.preferredColorScheme(.dark)
	}
}
