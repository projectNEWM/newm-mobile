import SwiftUI
import ModuleLinker
import Resolver
import Fonts
import Colors

protocol HomeScrollingCell: View where DataType: Identifiable {
	associatedtype DataType
	
	init(data: DataType)
}

struct HomeScrollingContentView<Model: HomeScrollingCell>: View {
	let selectedDataModel: (Model.DataType) -> ()
	let dataModels: [Model.DataType]
	let title: String
	let spacing: CGFloat
	
	var body: some View {
		VStack {
			sectionHeader(title: title)
			ScrollView(.horizontal, showsIndicators: false) {
				HStack(alignment: .center, spacing: nil) {
					ForEach(Array(dataModels.enumerated()), id: \.offset) { (offset, data) in
						Button(action: { selectedDataModel(data) }) {
							Model(data: data)
								.padding(.trailing, spacing)
						}
					}
				}
				.padding(.leading)
			}
			.tint(.white)
		}
	}
	
	private func sectionHeader(title: String) -> some View {
		Text(title)
			.frame(maxWidth: .infinity, alignment: .leading)
			.foregroundColor(Color(.grey100))
			.padding(.top)
			.padding(.leading)
			.font(.inter(ofSize: 12))
	}
}

struct HomeScrollingContentView_Previews: PreviewProvider {
	static var previews: some View {
		VStack {
//			HomeScrollingContentView<ArtistCell>(selectedDataModel: {_ in}, dataModels: MockData.artists.map(HomeViewModel.Artist.init), title: "NEWM Artists", spacing: 8)
			HomeScrollingContentView<SongCell>(selectedDataModel: {_ in}, dataModels: MockData.songs.map(HomeViewModel.Song.init), title: "NEWM Songs", spacing: 8)
			HomeScrollingContentView<PlaylistCell>(selectedDataModel: {_ in}, dataModels: MockData.playlists.map(HomeViewModel.Playlist.init), title: "Curated Playlists", spacing: 12)
		}
		.preferredColorScheme(.dark)
	}
}
