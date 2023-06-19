import SwiftUI
import ModuleLinker
import Resolver
import SharedUI
import Fonts
import AudioPlayer
import Models

public struct NowPlayingView: View {
	typealias Seconds = Int
	
	@Injected private var tipViewProvider: TipViewProviding
	@InjectedObject private var audioPlayer: AudioPlayerImpl
	
	var song: Song! { audioPlayer.song }
	
	@State var lyricsOffsetPercentage: Float = 0
	@State var showTipping: Bool = false
	@State var error: Error?
	
	public var body: some View {
		VStack {
			titleView
			Spacer()
			playbackCounter
			controlStrip
			tipButton.frame(width: 40, height: 40).padding(.top, 30)
		}
		.padding()
		.overlay {
			if showTipping {
				tippingOverlay
			}
		}
		.navigationTitle(String.nowPlayingTitle)
		.backButton()
	}
}

//MARK: Subviews
extension NowPlayingView {
	private var playbackCounter: some View {
		PlaybackCounter(
			currentTime: audioPlayer.playbackInfo.currentTime.playbackTimeString,
			totalTime: audioPlayer.playbackInfo.totalTime.playbackTimeString,
			percentComplete: audioPlayer.playbackInfo.percentPlayed,
			artistImageUrl: song.artist.image
		).aspectRatio(1, contentMode: .fit)
	}
	
	private var titleView: some View {
		HStack {
			favoriteButton
			Spacer()
			VStack(alignment: .center) {
				Text(song.title).font(.newmTitle1).padding(.bottom, 4)
				Text(song.artist.name)
			}.multilineTextAlignment(.center)
			Spacer()
			shareButton
		}
	}
	
	private var controlStrip: some View {
		HStack(alignment: .top) {
			shuffleButton
			Spacer()
			prevButton.padding(.top, 50)
			Spacer()
			PlayButton().frame(width: 80, height: 80).padding(.top, 50)
			Spacer()
			actionButton.padding(.top, 50)
			Spacer()
			repeatButton
		}
	}
	
	private var shuffleButton: some View {
		Button {
			audioPlayer.playbackInfo.shuffle.toggle()
		} label: {
			if audioPlayer.playbackInfo.shuffle == true {
				Asset.Media.PlayerIcons.shuffleSelected()
			} else {
				Asset.Media.PlayerIcons.shuffle()
			}
		}
	}
	
	private var prevButton: some View {
		Button {
			audioPlayer.prev()
		} label: {
			Asset.Media.PlayerIcons.previous()
		}
	}
	
	private var actionButton: some View {
		Button {
			audioPlayer.next()
		} label: {
			Asset.Media.PlayerIcons.next()
		}
	}
	
	private var repeatButton: some View {
		Button {
			audioPlayer.cycleRepeatMode()
		} label: {
			switch audioPlayer.playbackInfo.repeatMode {
			case .all:
				Asset.Media.PlayerIcons.Repeat.repeatFill()
			case .one:
				Asset.Media.PlayerIcons.Repeat.repeatOneFill()
			case .none:
				Asset.Media.PlayerIcons.Repeat.repeat()
			}
		}
		.frame(width: 48, height: 48)
	}
	
	private var favoriteButton: some View {
		Button {
			audioPlayer.song?.favorited.toggle()
		} label: {
			if song.favorited {
				Asset.Media.PlayerIcons.heartSelected()
			} else {
				Asset.Media.PlayerIcons.heart()
			}
		}
	}
	
	private var shareButton: some View {
		Button {
			//TODO:
		} label: {
			Asset.Media.PlayerIcons.share()
		}
	}
	
	private var tipButton: some View {
		Button {
			showTipping = true
		} label: {
			Asset.Media.PlayerIcons.heartAdd().resizable()
		}
	}
	
	private var tippingOverlay: some View {
		tipViewProvider.tipView { tip in
			withAnimation {
				showTipping = false
			}
		}
	}
}

private extension NowPlayingView {
	nonisolated static var playbackTimePlaceholder: String { "--:--" }
}

extension Int {
	private static let formatter = DateComponentsFormatter()
	
	var playbackTimeString: String {
		Int.formatter.allowedUnits = self > 3600 ? [.hour, .minute, .second] : [.minute, .second]
		Int.formatter.zeroFormattingBehavior = .pad
		guard let playbackTime = Int.formatter.string(from: TimeInterval(self)) else {
			//TODO:MU: Uncomment when KMM module added back
			return NowPlayingView.playbackTimePlaceholder
		}
		
		return playbackTime
	}
}

struct NowPlayingView_Previews: PreviewProvider {
	static var previews: some View {
		NowPlayingView()
			.preferredColorScheme(.dark)
	}
}
