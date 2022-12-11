import SwiftUI
import SharedUI
import shared
import Colors
import AudioPlayer
import Resolver

struct NFTCell: View {
	@Injected private var audioPlayer: AudioPlayerImpl
	let song: Song
	
    var body: some View {
		HStack {
			Asset.Media.playMiniFill.swiftUIImage
			HStack {
				AsyncImage(url: URL(string: song.image)) { image in
					image.circleImage(size: 24)
				} placeholder: {
					Image.placeholder.circleImage(size: 24)
				}

				Text(song.title)
				Spacer()
				//TODO: fix
				Text("Ɲ1.51")
			}
			.font(.inter(ofSize: 12))
			.padding()
			.background(NEWMColor.grey600())
			.cornerRadius(6)
		}
		.onTapGesture {
			audioPlayer.song = song
			audioPlayer.playbackInfo.isPlaying = true
		}
    }
}

struct NFTCell_Previews: PreviewProvider {
    static var previews: some View {
		NFTCell(song: MockData.songs.first!)
			.preferredColorScheme(.dark)
    }
}
