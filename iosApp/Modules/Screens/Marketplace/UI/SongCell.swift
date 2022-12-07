import SwiftUI
import SharedUI
import Colors
import ModuleLinker
import Resolver
import AudioPlayer
import SharedUI

struct SongCell: View {
	@Injected private var audioPlayer: AudioPlayerImpl
	let model: SongCellModel
	
	var body: some View {
		VStack(alignment: .leading) {
			image
			title
			artist
		}
		.overlay(alignment: .topTrailing) {
			price
		}
		.overlay {
			Asset.Media.playMiniFill.swiftUIImage
		}
		.lineLimit(1)
		.onTapGesture {
			audioPlayer.song = model.song
			audioPlayer.playbackInfo.isPlaying = true
		}
	}
	
	private var image: some View {
		AsyncImage(url: URL(string: model.imageUrl)) { image in
			image
				.resizable()
				.aspectRatio(1, contentMode: .fit)
		} placeholder: {
			Image.placeholder
		}
		.cornerRadius(6)
	}
	
	private var title: some View {
		Text(model.title)
			.font(Font.inter(ofSize: 14).weight(.semibold))
			.padding(.bottom, -3)
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

struct SongCell_Previews: PreviewProvider {
	static var previews: some View {
		Group {
			SongCell(model: MockData.songs.first!.trendingCellModel)
			HStack {
				SongCell(model: SongCellModel(song: MockData.songs.first!))
					.frame(width: 150)
				SongCell(model: SongCellModel(song: MockData.songs.first!))
					.frame(width: 200)
			}
		}
		.preferredColorScheme(.dark)
	}
}
