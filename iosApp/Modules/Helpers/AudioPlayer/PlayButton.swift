import SwiftUI
import SharedUI

//TODO: can't observe protocols so can't use InjectedObject with this.
public struct PlayButton: View {
	@ObservedObject private var audioPlayer = AudioPlayerImpl.shared
	
	public init() {}
	
	public var body: some View {
		Button {
			audioPlayer.playbackInfo.isPlaying.toggle()
		} label: {
			audioPlayer.playbackInfo.isPlaying ?
			Asset.Media.PlayerIcons.pause.swiftUIImage.resizable() :
			Asset.Media.PlayerIcons.play.swiftUIImage.resizable()
		}
		.aspectRatio(contentMode: .fit)
	}
}
