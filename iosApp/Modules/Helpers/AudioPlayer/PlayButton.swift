import SwiftUI
import SharedUI
import Resolver
import ModuleLinker

public struct PlayButton: View {
	@InjectedObject private var audioPlayer: VLCAudioPlayer
	
	public init() {}
	
	public var body: some View {
		Button {
			if audioPlayer.isPlaying {
				audioPlayer.pause()
			} else {
				audioPlayer.play()
			}
		} label: {
			if audioPlayer.isPlaying {
				Asset.Media.PlayerIcons.pause().resizable()
			} else {
				Asset.Media.PlayerIcons.play().resizable()
			}
		}
		.aspectRatio(contentMode: .fit)
	}
}
