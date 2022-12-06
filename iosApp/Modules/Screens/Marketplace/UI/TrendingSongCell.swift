import SwiftUI
import SharedUI
import Colors
import ModuleLinker

struct TrendingSongCell: View {
	let model: TrendingSongCellModel
	
	var body: some View {
		VStack(alignment: .leading) {
			image
			title
			artist
		}
		.overlay(alignment: .topTrailing) {
			price
		}
		.frame(width: 200)
		.lineLimit(1)
	}
	
	private var image: some View {
		AsyncImage(url: URL(string: model.imageUrl)) { image in
			image
				.resizable()
				.aspectRatio(1, contentMode: .fit)
		} placeholder: {
			Image.placeholder
		}
		.padding(.bottom, 6)
		.cornerRadius(6)
	}
	
	private var title: some View {
		Text(model.title)
			.font(Font.inter(ofSize: 14).weight(.semibold))
			.padding(.bottom, 5)
			.truncationMode(.tail)
	}
	
	private var artist: some View {
		HStack {
			AsyncImage(url: URL(string: model.artistUrl)) { image in
				image.circleImage(size: 28)
			} placeholder: {
				Image.placeholder
			}
			Text(model.artistName)
				.font(Font.interMedium(ofSize: 12))
				.foregroundColor(NEWMColor.grey100.swiftUIColor)
		}
		.frame(height: 28)
	}
	
	private var price: some View {
		Text(model.nftPrice)
			.padding(6)
			.background(Color.black.opacity(0.3))
			.cornerRadius(6)
			.padding(6)
	}
}

struct TrendingSongCell_Previews: PreviewProvider {
	static var previews: some View {
		Group {
			TrendingSongCell(model: MockData.songs.first!.trendingCellModel)
			TrendingSongCell(model: TrendingSongCellModel(id: "1",
														  imageUrl: MockData.url(for: Asset.MockAssets.artist1),
														  title: "Song with a reallly long title that keeps going and going.",
														  artistUrl: MockData.url(for: Asset.MockAssets.artist0),
														  artistName: "Joe billy bob real loing name guy"))
		}
		.preferredColorScheme(.dark)
	}
}
