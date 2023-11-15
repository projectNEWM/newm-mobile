import SwiftUI
import SharedUI
import AudioPlayer
import Resolver
import ModuleLinker
import Colors

struct MiniPlayerView: View {
	@Injected private var audioPlayer: AudioPlayer
	@InjectedObject private var audioPlayerPublisher: AudioPlayerPublisher
		
	private let iconSize: CGFloat = 32
	
	var body: some View {
		VStack(alignment: .leading, spacing: 0) {
			progress.frame(height: 1)
			HStack(alignment: .center) {
				image.fixedSize()
				songInfo
				Spacer()
				PlayButton().frame(width: iconSize, height: iconSize)
			}
			.padding()
		}
		.background(.black)
	}
	
	@ViewBuilder
	private var progress: some View {
		GeometryReader { geometry in
			LinearGradient(colors: [
//				NEWMColor.yellow(),
//				NEWMColor.orange(),
//				NEWMColor.red(),
//				NEWMColor.pink(),
//				NEWMColor.purple(),
//				NEWMColor.blue()
				try! Color(hex: "DC3CAA")
			],
						   startPoint: .leading,
						   endPoint: UnitPoint(x: (geometry.size.width / CGFloat(audioPlayerPublisher.percentPlayed ?? 0)) / geometry.size.width, y: 0.5))
			.frame(width: geometry.size.width * CGFloat(audioPlayerPublisher.percentPlayed ?? 0), height: 1)
		}
	}
	
	private var songInfo: some View {
		VStack(alignment: .leading) {
			Text(audioPlayer.currentItem?.title ?? "")
				.font(.inter(ofSize: 14).bold())
			Text(audioPlayer.currentItem?.artist ?? "")
				.font(.inter(ofSize: 12))
		}
	}
	
	@ViewBuilder
	private var image: some View {
		if let image = audioPlayer.currentItem?.artwork?.image(at: CGSize(width: iconSize, height: iconSize)) {
//			AsyncImage(
//				url: imageUrl) { image in
//					image.circleImage(size: iconSize)
//				} placeholder: {
//					Image.placeholder.circleImage(size: iconSize)
//				}
//				.padding(.trailing, 8)
			Image(uiImage: image)
				.padding(.trailing, 8)
		}
	}
}

struct MiniPlayerView_Previews: PreviewProvider {
	@Injected static private var audioPlayer: AudioPlayer
	
	static var previews: some View {
//		audioPlayer.song = MockData.songs.first!
		return ZStack {
			Color.white.erased
			MiniPlayerView()
				.preferredColorScheme(.dark)
		}
	}
}
