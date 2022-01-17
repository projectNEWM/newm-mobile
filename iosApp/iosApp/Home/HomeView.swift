import SwiftUI

struct HomeView: View {
	@ObservedObject var viewModel = HomeViewModel()
	
	var body: some View {
		NavigationView {
			ZStack {
				allViews
					.frame(maxHeight: .infinity, alignment: .top)
					.padding()
					.background(Color.black)
					.onAppear { viewModel.deselectAll() }
				IDLink<ArtistView>(selectedID: viewModel.selectedArtist?.artistID)
				IDLink<SongView>(selectedID: viewModel.selectedSong?.songID)
				IDLink<PlaylistDetailsView>(selectedID: viewModel.selectedPlaylist?.playlistID)
			}
			.navigationTitle(viewModel.title)
			.navigationBarTitleDisplayMode(.automatic)
		}
	}
	
	private var allViews: some View {
		ScrollView {
			VStack {
				SectionSelectorView(selectedIndex: $viewModel.selectedSectionIndex, sectionTitles: viewModel.sections)
				HomeScrollingContentView<ArtistCell>(selectedDataModel: $viewModel.selectedArtist, dataModels: viewModel.artists, title: viewModel.artistSectionTitle, spacing: 8)
				HomeScrollingContentView<SongCell>(selectedDataModel: $viewModel.selectedSong, dataModels: viewModel.songs, title: viewModel.songsSectionTitle, spacing: 8)
				HomeScrollingContentView<PlaylistCell>(selectedDataModel: $viewModel.selectedPlaylist, dataModels: viewModel.playlists, title: viewModel.playlistsSectionTitle, spacing: 12)
			}
		}
	}
}

struct HomeView_Previews: PreviewProvider {
	static var previews: some View {
		HomeView()
	}
}
