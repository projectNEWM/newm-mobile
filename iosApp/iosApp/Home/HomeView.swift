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
					.onAppear { viewModel.selectedArtist = nil }
				NavigationLink(isActive: .constant(viewModel.selectedArtist != nil), destination: {
					if let artist = viewModel.selectedArtist {
						ArtistView(artist: artist)
					} else {
						EmptyView()
					}
				}, label: { EmptyView() })
			}
			.navigationTitle("NEWM Muthafucka!")
			.navigationBarTitleDisplayMode(.automatic)
		}
	}
	
	private var allViews: some View {
		VStack {
			SectionSelectorView(selectedIndex: $viewModel.selectedSectionIndex, sectionTitles: viewModel.sections)
			HomeScrollingContentView<NewmArtistCell>(selectedDataModel: $viewModel.selectedArtist, dataModels: viewModel.newmArtists, title: "NEWM Artists")
			HomeScrollingContentView<NewmSongCell>(selectedDataModel: $viewModel.selectedSong, dataModels: viewModel.newmSongs, title: "NEWM Songs")
		}
	}
}

struct HomeView_Previews: PreviewProvider {
	static var previews: some View {
		HomeView()
	}
}
