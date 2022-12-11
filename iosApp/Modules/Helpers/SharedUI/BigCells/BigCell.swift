import SwiftUI
import ModuleLinker
import Resolver
import Fonts
import Colors

public struct BigCell: View {
	private let model: BigCellViewModel
	
	private let titleFont: Font = .inter(ofSize: 12).bold()
	private let subtitleFont: Font = .inter(ofSize: 12)
	private let subtitleColor = NEWMColor.grey100()
	private let imageSize: CGFloat = 130
	
	public init(model: BigCellViewModel) {
		self.model = model
	}
	
	public var body: some View {
		VStack(alignment: .leading) {
			artistImage
			artistName
			genre
		}
		.frame(width: imageSize)
		.onTapGesture(perform: model.onTap)
	}
	
	private var artistImage: some View {
		AsyncImage(url: model.image) { phase in
			switch phase {
			case .success(let image):
				image
					.resizable()
					.frame(width: imageSize, height: imageSize)
					.cornerRadius(10)
			case .empty:
				Image.placeholder
					.resizable()
					.frame(width: imageSize, height: imageSize)
					.cornerRadius(10)
			case .failure(let error):
				Text(error.localizedDescription)
			@unknown default:
				fatalError()
			}
		}
		.cornerRadius(10)

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

struct BigCell_Previews: PreviewProvider {
	static var previews: some View {
		ScrollView(.horizontal) {
			HStack {
				ForEach(MockData.bigArtistCells_shuffled(seed: 1, onTap: {_ in}).reversed(), id: \.id) { model in
					BigCell(model: model)
				}
			}
			.preferredColorScheme(.dark)
		}
	}
}
