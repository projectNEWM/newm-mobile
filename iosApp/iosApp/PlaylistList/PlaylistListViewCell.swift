import SwiftUI

struct PlaylistListViewCell: View {
	let playlist: PlaylistListViewModel.Playlist
	let imageSize: CGFloat = 80
	
	var body: some View {
		HStack {
			CircleImage(image: playlist.image, size: imageSize)
			VStack {
				Text(playlist.title)
					.foregroundColor(.white)
				Text(playlist.creator)
					.foregroundColor(.white)
				HStack {
					Text(playlist.genre)
					Text(playlist.starCount)
					Text(playlist.playCount)
				}
			}
		}
		.padding()
		.padding(.trailing)
		.background(LinearGradient(colors: [.purple, .pink], startPoint: .top, endPoint: .bottom))
		.cornerRadius(imageSize)
	}
}

struct PlaylistViewCell_Previews: PreviewProvider {
	static var previews: some View {
		PlaylistListViewCell(playlist: DummyData.makePlaylistListPlaylist(id: "1"))
	}
}
