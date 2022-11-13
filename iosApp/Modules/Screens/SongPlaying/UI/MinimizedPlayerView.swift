import SwiftUI
import SharedUI
import AudioPlayer
import Resolver
import ModuleLinker
import shared
import Colors

struct MinimizedPlayerView: View {
	@ObservedObject private var audioPlayer = AudioPlayerImpl.shared
		
	private let iconSize: CGFloat = 32
	
    var body: some View {
		VStack(alignment: .leading) {
			progress
			HStack {
				image
				songInfo
				Spacer()
				PlayButton()
			}
			.frame(height: 40)
			.padding(12)
		}
		.frame(height: 40)
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

struct MinimizedPlayerView_Previews: PreviewProvider {
	@Injected static private var audioPlayer: any AudioPlayer

    static var previews: some View {
		audioPlayer.song = MockData.songs.first!
		audioPlayer.playbackInfo.isPlaying = true
		return MinimizedPlayerView()
			.preferredColorScheme(.dark)
    }
}