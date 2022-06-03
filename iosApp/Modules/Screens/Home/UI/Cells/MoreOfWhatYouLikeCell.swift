import SwiftUI
import ModuleLinker
import Resolver
import SharedUI
import Fonts
import Colors

struct MoreOfWhatYouLikeCell: View {
	let model: HomeViewModel.MoreOfWhatYouLike
	let titleFont: Font
	let subtitleFont: Font
	let subtitleColor: Color
	private let imageSize: CGFloat = 120
	
	public var body: some View {
		VStack(alignment: .leading) {
			artistImage
			artistName
			genre
		}
		.frame(width: imageSize)
	}
	
	private var artistImage: some View {
		AsyncImage(url: model.image) { phase in
			switch phase {
			case .success(let image):
				image
					.resizable()
					.frame(width: imageSize, height: imageSize)
			default:
				Image(uiImage: .placeholder!)
					.resizable()
					.frame(width: imageSize, height: imageSize)
			}
		}
	}
	
	private var artistName: some View {
		Text(model.name)
			.foregroundColor(.white)
			.lineLimit(1)
			.multilineTextAlignment(.center)
			.font(titleFont)
			.padding(.bottom, 1)
			.truncationMode(.tail)
	}

	private var genre: some View {
		Text(model.genre)
			.font(subtitleFont)
			.foregroundColor(subtitleColor)
			.padding(.bottom, 1)
			.padding(.top, -8)
	}
}

struct MoreOfWhatYouLikeCell_Previews: PreviewProvider {
	static var previews: some View {
		ScrollView(.horizontal) {
			HStack {
				ForEach(MockData.moreOfWhatYouLikeCells.reversed(), id: \.id) { model in
					MoreOfWhatYouLikeCell(model: model, titleFont: .inter(ofSize: 12).bold(), subtitleFont: .inter(ofSize: 12), subtitleColor: Color(.grey100))
				}
			}
			.preferredColorScheme(.dark)
		}
	}
}
