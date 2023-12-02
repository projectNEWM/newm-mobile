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
				Image(systemName: "pause.fill")
			} else {
				Image(systemName: "play.fill")
			}
		}
		.tint(.white)
	}
}

#Preview {
	PlayButton()
		.preferredColorScheme(.dark)
}
