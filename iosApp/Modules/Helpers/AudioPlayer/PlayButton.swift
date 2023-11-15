import SwiftUI
import SharedUI
import Resolver
import ModuleLinker

public struct PlayButton: View {
	@Injected private var audioPlayer: AudioPlayer
	
	public init() {}
	
	public var body: some View {
		Button {
//			audioPlayer.playbackInfo.isPlaying.toggle()
		} label: {
//			audioPlayer.playbackInfo.isPlaying ?
//			Asset.Media.PlayerIcons.pause().resizable() :
//			Asset.Media.PlayerIcons.play().resizable()
		}
		.aspectRatio(contentMode: .fit)
	}
}
