import SwiftUI
import Colors

public struct CompactCell: View {
	private let model: CompactCellModel
	//TODO: image doesnt size properly when set false
	private let roundImage: Bool
	
	private let titleFont: Font = .inter(ofSize: 12).bold()
	private let subtitleFont: Font = .inter(ofSize: 12)
	private let subtitleColor: Color = NEWMColor.grey100.swiftUIColor
	
	private let imageSize: CGFloat = 60
	
	public init(model: CompactCellModel, roundImage: Bool) {
		self.model = model
		self.roundImage = roundImage
	}
	
	public var body: some View {
		HStack {
			image
			VStack(alignment: .leading) {
				title
				songs
			}
		}
    }
	
	private var image: some View {
		AsyncImage(url: model.image) { phase in
			switch phase {
			case .success(let image):
				if roundImage {
					return image.circleImage(size: imageSize).erased
				} else {
					return image.resizable().frame(width: imageSize, height: imageSize).erased
				}
			default:
				Image.placeholder.circleImage(size: imageSize)
			}
		}
	}
	
	private var title: some View {
		Text(model.name)
			.lineLimit(1)
			.font(titleFont)
	}
	
	private var songs: some View {
		Text(model.subtitle)
			.font(subtitleFont)
			.foregroundColor(subtitleColor)
	}
}

struct CompactCell_Previews: PreviewProvider {
    static var previews: some View {
		Group {
			CompactCell(model: MockData.compactArtistCells.first!, roundImage: false)
			CompactCell(model: MockData.compactArtistCells.first!, roundImage: true)
		}
		.preferredColorScheme(.dark)
    }
}
