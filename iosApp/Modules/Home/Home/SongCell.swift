import SwiftUI
import SharedUI
import Colors

struct SongCell: View {
	var data: HomeViewModel.Song
	
	var body: some View {
		VStack {
			ZStack(alignment: .top) {
				CircleImage(data.image, size: 60)
				if data.isNFT {
					GradientTag(title: "NFT")
						.padding(.top, -14)
				}
			}
			Text(data.title)
				.foregroundColor(.white)
				.font(.caption3)
			Text(data.artist)
				.foregroundColor(.newmOrange)
				.font(.caption4)
		}
		.fixedSize()
		.frame(width: 105, height: 125)
	}
}

extension SongCell: HomeScrollingCell {
	typealias DataType = HomeViewModel.Song
}

struct SongCell_Previews: PreviewProvider {
	static var previews: some View {
		Group {
			SongCell(data: DummyData.makeSong(title: "Song", isNFT: false))
			SongCell(data: DummyData.makeSong(title: "Song", isNFT: true))
		}
		.preferredColorScheme(.dark)
	}
}
