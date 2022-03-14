import SwiftUI
import SharedUI
import Fonts

struct PlaylistCell: View {
	let data: HomeViewModel.Playlist
	
    var body: some View {
		VStack {
			playlistImage
			title
			creator
			songCount
		}
		.padding(10)
		.frame(width: 107, height: 191, alignment: .top)
		.background(backgroundGradient)
		.cornerRadius(60)
    }
	
	private var playlistImage: some View {
		CircleImage(image: data.image, size: 70)
			.padding(.top, 5)
	}
	
	private var title: some View {
		Text(data.title)
			.multilineTextAlignment(.center)
			.foregroundColor(.white)
			.font(.robotoMedium(ofSize: 14))
			.padding(.bottom, 2)
			.lineLimit(2)
			.minimumScaleFactor(0.5)
	}
	
	private var creator: some View {
		Text(data.creator)
			.foregroundColor(.newmPink.opacity(0.97))
			.font(.roboto(ofSize: 11))
			.padding(.bottom, 2)
	}
	
	private var songCount: some View {
		Text(data.songCount)
			.foregroundColor(.white)
			.font(.roboto(ofSize: 11))
			.padding(.bottom, 4)
	}
	
	private var backgroundGradient: some View {
		LinearGradient(colors: [.newmOffPink, .newmYellow], startPoint: .top, endPoint: .bottom).opacity(0.26)
	}
}

extension PlaylistCell: HomeScrollingCell {
	typealias DataType = HomeViewModel.Playlist
}

struct PlaylistCell_Previews: PreviewProvider {
    static var previews: some View {
		PlaylistCell(data: DummyData.makeHomePlaylist(id: "1"))
			.preferredColorScheme(.dark)
    }
}
