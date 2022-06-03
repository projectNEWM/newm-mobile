import SwiftUI
import SharedUI

struct BigArtistCell: View {
	let model: HomeViewModel.Artist
	
	let titleFont: Font
	let subtitleFont: Font
	let subtitleColor: Color
	
	private let imageSize: CGFloat = 50
	
    var body: some View {
		HStack {
			image
			VStack(alignment: .leading) {
				title
				songs
			}
		}
		.fixedSize()
    }
	
	private var image: some View {
		AsyncImage(url: model.image) { phase in
			switch phase {
			case .success(let image):
				image.circleImage(size: imageSize)
			default:
				Image.placeholder?.circleImage(size: imageSize)
			}
		}
	}
	
	private var title: some View {
		Text(model.name)
	}
	
	private var songs: some View {
		Text(model.numberOfSongs)
	}
}

struct ArtistCell_Previews: PreviewProvider {
    static var previews: some View {
		BigArtistCell(model: MockData.artistCells.first!, titleFont: .inter(ofSize: 12).bold(), subtitleFont: .inter(ofSize: 12), subtitleColor: Color(.grey100))
			.preferredColorScheme(.dark)
    }
}
