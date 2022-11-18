import SwiftUI
import SharedUI
import AudioPlayer
import Resolver
import ModuleLinker
import shared
import Colors

struct MiniPlayerView: View {
	@InjectedObject private var audioPlayer: AnyAudioPlayer
	
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
				NEWMColor.yellow.swiftUIColor,
				NEWMColor.orange.swiftUIColor,
				NEWMColor.red.swiftUIColor,
				NEWMColor.pink.swiftUIColor,
				NEWMColor.purple.swiftUIColor,
				NEWMColor.blue.swiftUIColor
			],
						   startPoint: .leading,
						   endPoint: UnitPoint(x: (geometry.size.width / audioPlayer.playbackInfo.percentPlayed) / geometry.size.width, y: 0.5))
			.frame(width: geometry.size.width * audioPlayer.playbackInfo.percentPlayed, height: 1)
		}
	}
	
	private var songInfo: some View {
		VStack(alignment: .leading) {
			Text(audioPlayer.song?.title ?? "")
				.font(.inter(ofSize: 14).bold())
			Text(audioPlayer.song?.artist.name ?? "")
				.font(.inter(ofSize: 12))
		}
	}
	
	@ViewBuilder
	private var image: some View {
		if let imageUrl = audioPlayer.song?.image {
			AsyncImage(
				url: URL(string: imageUrl)) { image in
					image.circleImage(size: iconSize)
				} placeholder: {
					Image.placeholder
				}
				.padding(.trailing, 8)
		}
	}
}

struct MiniPlayerView_Previews: PreviewProvider {
	@Injected static private var audioPlayer: AnyAudioPlayer
	
	static var previews: some View {
		audioPlayer.song = MockData.songs.first!
		return ZStack {
			Color.white.erased
			MiniPlayerView()
				.preferredColorScheme(.dark)
		}
	}
}
