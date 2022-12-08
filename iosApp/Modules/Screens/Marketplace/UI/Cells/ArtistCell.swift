import SwiftUI
import SharedUI
import Colors

struct ArtistCell: View {
	let model: ArtistCellModel
	
	var body: some View {
		VStack(spacing: 4) {
			AsyncImage(url: URL(string: model.imageUrl)) { image in
				image.circleImage(size: 100)
			} placeholder: {
				Image.placeholder
			}
			Text(model.name)
				.font(.inter(ofSize: 12).bold())
				.padding(.top, 8)
			//TODO: localize
			Text("\(model.songsCount) songs")
				.font(.inter(ofSize: 12))
				.foregroundColor(NEWMColor.grey100())
		}
		.lineLimit(1)
		.frame(width: 100)
	}
}

struct ArtistCell_Previews: PreviewProvider {
	static var previews: some View {
		Group {
			ArtistCell(model: ArtistCellModel(artist: MockData.artists.first!)).border(.white)
				.frame(width: 200)
				.fixedSize()
		}
		.preferredColorScheme(.dark)
	}
}
