import SwiftUI

struct PlaylistCell: View {
	let data: HomeViewModel.Playlist
	
    var body: some View {
		VStack {
			CircleImage(image: data.image, size: 60)
				.padding(.top, 5)
			Text(data.title)
				.multilineTextAlignment(.center)
				.foregroundColor(.white)
				.font(.caption)
				.padding(.bottom, 2)
				.lineLimit(2)
				.minimumScaleFactor(0.5)
			Text(data.creator)
				.foregroundColor(.purple)
				.font(.caption3)
				.padding(.bottom, 2)
			Text(data.songCount)
				.foregroundColor(.white)
				.font(.caption4)
				.padding(.bottom, 4)
		}
		.frame(width: 75, height: 150, alignment: .top)
		.padding(10)
		.background(LinearGradient(colors: [.purple, .pink], startPoint: .top, endPoint: .bottom))
		.cornerRadius(60)
    }
}

extension PlaylistCell: HomeScrollingCell {
	typealias DataType = HomeViewModel.Playlist
}

struct PlaylistCell_Previews: PreviewProvider {
    static var previews: some View {
		PlaylistCell(data: DummyData.makeHomePlaylist(id: "1"))
    }
}
