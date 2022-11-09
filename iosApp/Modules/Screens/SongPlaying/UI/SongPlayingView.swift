import SwiftUI
import ModuleLinker
import Resolver
import SharedUI
import Fonts
import shared

public struct SongPlayingView: View {
	typealias Seconds = Int
	
	@Injected private var tipViewProvider: TipViewProviding
	//TODO: can't declare protocol for this.
	@ObservedObject private var audioPlayer = AudioPlayerImpl.shared
	
	@State var song: SongInfo
	
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
		.task {
			audioPlayer.setSongId(SharedUI.MockData.songs.first!.songId)
		}
	}
	
	private var playbackCounter: some View {
		PlaybackCounter(
			currentTime: audioPlayer.currentTime.playbackTimeString,
			totalTime: audioPlayer.totalTime.playbackTimeString,
			percentComplete: audioPlayer.percentPlayed,
			artistImageUrl: song.artist.image
		).aspectRatio(1, contentMode: .fit)
	}
	
	private var titleView: some View {
		HStack {
			favoriteButton
			Spacer()
			VStack {
				Text(song.songTitle).font(.newmTitle1)
				Text(song.artist.name)
			}
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
			playButton.padding(.top, 50)
			Spacer()
			nextButton.padding(.top, 50)
			Spacer()
			repeatButton
		}
	}
	
	private var shuffleButton: some View {
		Button {
			audioPlayer.shuffle.toggle()
		} label: {
			if audioPlayer.shuffle {
				Asset.Media.PlayerIcons.shuffleSelected.swiftUIImage
			} else {
				Asset.Media.PlayerIcons.shuffle.swiftUIImage
			}
		}
	}
	
	private var prevButton: some View {
		Button {
			audioPlayer.prev()
		} label: {
			Asset.Media.PlayerIcons.previous.swiftUIImage
		}
	}
	
	private var playButton: some View {
		Button {
			audioPlayer.isPlaying.toggle()
		} label: {
			audioPlayer.isPlaying ?
			Asset.Media.PlayerIcons.pause.swiftUIImage :
			Asset.Media.PlayerIcons.play.swiftUIImage
		}
	}
	
	private var nextButton: some View {
		Button {
			audioPlayer.next()
		} label: {
			Asset.Media.PlayerIcons.next.swiftUIImage
		}
	}
	
	private var repeatButton: some View {
		Button {
			audioPlayer.cycleRepeatMode()
		} label: {
			//TODO: add different repeat image states
			Asset.Media.PlayerIcons.repeat.swiftUIImage
		}
	}
	
	private var favoriteButton: some View {
		Button {
			song.favorited.toggle()
		} label: {
			if song.favorited {
				Asset.Media.PlayerIcons.heartSelected.swiftUIImage
			} else {
				Asset.Media.PlayerIcons.heart.swiftUIImage
			}
		}
	}
	
	private var shareButton: some View {
		Button {
			//TODO:
		} label: {
			Asset.Media.PlayerIcons.share.swiftUIImage
		}
	}
	
	private var tipButton: some View {
		Button {
			showTipping = true
		} label: {
			Asset.Media.PlayerIcons.heartAdd.swiftUIImage.resizable()
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

struct SongPlayingView_Previews: PreviewProvider {
	static var previews: some View {
		SongPlayingView(song: MockData.songInfo())
			.preferredColorScheme(.dark)
	}
}



struct BackButton: ViewModifier {
	@Environment(\.presentationMode) @Binding var presentationMode: PresentationMode
	
	func body(content: Content) -> some View {
		content
			.navigationBarBackButtonHidden(true)
			.navigationBarItems(leading: btnBack)
	}
	
	var btnBack: some View {
		Button(action: { presentationMode.dismiss() }) {
			HStack {
				Asset.Media.backArrow.swiftUIImage
					.aspectRatio(contentMode: .fit)
					.foregroundColor(.white)
			}
		}
	}
}

extension View {
	func backButton() -> some View {
		modifier(BackButton())
	}
}

private extension SongPlayingView {
	nonisolated static var playbackTimePlaceholder: String { "--:--" }
}

extension Int {
	private static let formatter = DateComponentsFormatter()

	var playbackTimeString: String {
		Int.formatter.allowedUnits = self > 3600 ? [.hour, .minute, .second] : [.minute, .second]
		Int.formatter.zeroFormattingBehavior = .pad
		guard let playbackTime = Int.formatter.string(from: TimeInterval(self)) else {
			//TODO:MU: Uncomment when KMM module added back
			return SongPlayingView.playbackTimePlaceholder
		}

		return playbackTime
	}
}
