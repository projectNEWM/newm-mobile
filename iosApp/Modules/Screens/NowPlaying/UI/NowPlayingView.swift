import SwiftUI
import ModuleLinker
import Resolver
import SharedUI
import Fonts
import AudioPlayer
import Models
import shared

public struct NowPlayingView: View {
	typealias Seconds = Int
	
	@Injected private var audioPlayer: AudioPlayer
	
	var track: AudioItem! { audioPlayer.currentItem }
	
	@State var error: Error?
	
	public var body: some View {
		ZStack {
			VStack {
				Spacer()
				title
				controls
			}
			.padding()
		}
		.background(background)
		.backButton()
	}
}

//MARK: Subviews
extension NowPlayingView {
	@ViewBuilder
	private var background: some View {
		if let image = track.artworkImage {
			Image(uiImage: image)
				.resizable(resizingMode: .stretch)
				.scaledToFill()
		} else {
			Image.placeholder
				.resizable(resizingMode: .stretch)
				.scaledToFill()
		}
	}
	
	private var title: some View {
		VStack(alignment: .center) {
			Text(track.title ?? "--")
				.font(Font.ralewayExtraBold(ofSize: 24))
			Text(track.artist ?? "--")
				.font(Font.interMedium(ofSize: 14))
		}
	}
	
	private var playbackTimeBinding: Binding<Float> {
		Binding<Float>(get: {
			Float(audioPlayer.currentItemProgression ?? 0)
		}, set: { playbackTime in
			audioPlayer.seek(to: Double(playbackTime))
		})
	}
	
	fileprivate var controls: some View {
		VStack(spacing: 0) {
			if let duration = audioPlayer.currentItemDuration, duration > 0 {
				Slider(value: playbackTimeBinding, in: 0.0...Float(duration))
				//					.tint(try! Color(hex: "161618"))
					.padding(.bottom, -13)
					.zIndex(1)
			}
			
			VStack {
				VStack {
					HStack {
						Text("\(audioPlayer.currentItemProgression.playbackTimeString)")
						Spacer()
						Text("\(audioPlayer.currentItemDuration.playbackTimeString)")
					}
					.foregroundStyle(try! Color(hex: "8F8F91"))
					.font(Font.inter(ofSize: 12))
					Spacer()
					HStack {
						repeatButton
							.frame(width: 40, height: 40)
						Spacer()
						prevButton.padding(.trailing)
							.frame(width: 40, height: 40)
						PlayButton().padding([.trailing, .leading])
							.frame(width: 40, height: 40)
						nextButton.padding(.leading)
							.frame(width: 40, height: 40)
						Spacer()
						shareButton
							.frame(width: 40, height: 40)
					}
				}
			}
			.padding()
			.background(Color.black)
			.clipShape(UnevenRoundedRectangle(cornerRadii: RectangleCornerRadii(bottomLeading: 8, bottomTrailing: 8), style: .continuous))
			.frame(height: 108)
		}
	}
	
	//	private var shuffleButton: some View {
	//		Button {
	//			audioPlayer.playbackInfo.shuffle.toggle()
	//		} label: {
	//			if audioPlayer.playbackInfo.shuffle == true {
	//				Asset.Media.PlayerIcons.shuffleSelected()
	//			} else {
	//				Asset.Media.PlayerIcons.shuffle()
	//			}
	//		}
	//	}
	
	private var prevButton: some View {
		Button {
//			audioPlayer.prev()
		} label: {
			Asset.Media.PlayerIcons.previous()
		}
	}
	
	private var nextButton: some View {
		Button {
			audioPlayer.next()
		} label: {
			Asset.Media.PlayerIcons.next()
		}
	}
	
	private var repeatButton: some View {
		Button {
//			audioPlayer.cycleRepeatMode()
		} label: {
//			switch audioPlayer.playbackInfo.repeatMode {
//			case .all:
//				Asset.Media.PlayerIcons.Repeat.repeatFill()
//			case .one:
//				Asset.Media.PlayerIcons.Repeat.repeatOneFill()
//			case .none:
//				Asset.Media.PlayerIcons.Repeat.repeat()
//			}
		}
		.frame(width: 48, height: 48)
	}
	
	private var shareButton: some View {
		Button {
			//TODO:
		} label: {
			Asset.Media.PlayerIcons.share()
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

extension Optional<Int> {
	var playbackTimeString: String {
		(self ?? 0).playbackTimeString
	}
}

extension Float {
	var playbackTimeString: String {
		Int(self).playbackTimeString
	}
}

extension Double {
	var playbackTimeString: String {
		Int(self).playbackTimeString
	}
}

extension Optional<Double> {
	var playbackTimeString: String {
		Int(self ?? 0).playbackTimeString
	}
}

//struct NowPlayingView_Previews: PreviewProvider {
//	static var previews: some View {
//		AudioPlayerModule.shared.registerAllServices()
//		@InjectedObject var audioPlayer: AudioPlayerImpl
//		audioPlayer.track = AudioTrack(nftTrack: NFTTrack(title: "Blowin it", artistName: "Joe Blow", url: Bundle(for: AudioPlayerModule.self).url(forResource: "getSchwifty", withExtension: "mp3")!, image: url(for: Asset.MockAssets.artist0)))
//		return Group {
//			NowPlayingView()
//				.preferredColorScheme(.dark)
//			NowPlayingView().controls
//		}.padding()
//	}
//}

//private extension AudioTrack {
//	init(nftTrack: ModuleLinker.NFTTrack) {
//		self = AudioTrack(
//			title: nftTrack.title,
//			artistName: nftTrack.artistName,
//			url: nftTrack.url,
//			image: nftTrack.image
//		)
//	}
//}

func url(for testImage: ImageAsset) -> URL {
	guard let imageURL = NSURL(fileURLWithPath: NSTemporaryDirectory()).appendingPathComponent("\(testImage.name).png") else {
		fatalError()
	}
	
	let pngData = testImage.image.pngData()
	do { try pngData?.write(to: imageURL) } catch { }
	return imageURL
}
