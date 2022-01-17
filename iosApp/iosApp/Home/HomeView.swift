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
				Link<ArtistView>(selectedID: viewModel.selectedArtist?.artistID)
				Link<SongView>(selectedID: viewModel.selectedSong?.songID)
				Link<PlaylistView>(selectedID: viewModel.selectedPlaylist?.playlistID)
			}
			.navigationTitle("NEWM")
			.navigationBarTitleDisplayMode(.automatic)
		}
	}
	
	private var allViews: some View {
		ScrollView {
			VStack {
				SectionSelectorView(selectedIndex: $viewModel.selectedSectionIndex, sectionTitles: viewModel.sections)
				HomeScrollingContentView<ArtistCell>(selectedDataModel: $viewModel.selectedArtist, dataModels: viewModel.artists, title: "NEWM Artists", spacing: 8)
				HomeScrollingContentView<SongCell>(selectedDataModel: $viewModel.selectedSong, dataModels: viewModel.songs, title: "NEWM Songs", spacing: 8)
				HomeScrollingContentView<PlaylistCell>(selectedDataModel: $viewModel.selectedPlaylist, dataModels: viewModel.playlists, title: "Curated Playlists", spacing: 12)
			}
		}
	}
}

struct HomeView_Previews: PreviewProvider {
	static var previews: some View {
		HomeView()
	}
}
