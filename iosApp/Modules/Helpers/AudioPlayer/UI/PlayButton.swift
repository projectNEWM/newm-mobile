import SwiftUI
import SharedUI
import Resolver
import ModuleLinker

public struct PlayButton: View {
	@InjectedObject private var audioPlayer: VLCAudioPlayer
	
	public init() {}
	
	public var body: some View {
		Button {
			switch audioPlayer.state {
			case .playing:
				audioPlayer.pause()
			case .paused, .stopped:
				audioPlayer.play()
			case .buffering:
				break
			}
		} label: {
			switch audioPlayer.state {
			case .buffering:
				ProgressView()
			case .paused, .stopped:
				playButton
			case .playing:
				pauseButton
			}
		}
		.tint(.white)
		.disabled(isDisabled)
	}
	
	@ViewBuilder
	private var playButton: some View {
		Image(systemName: "play.fill")
	}
	
	@ViewBuilder
	private var pauseButton: some View {
		Image(systemName: "pause.fill")
	}
	
	private var isDisabled: Bool {
		audioPlayer.state == .buffering
	}
}

#Preview {
	PlayButton()
		.preferredColorScheme(.dark)
}
