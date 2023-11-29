import SwiftUI
import ModuleLinker
import Resolver
import SharedUI
import Fonts
import AudioPlayer
import Models
import shared
import Kingfisher
import Colors

public struct NowPlayingView: View {
	typealias Seconds = Int
	
	@InjectedObject private var audioPlayer: VLCAudioPlayer
		
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
		KFImage(audioPlayer.artworkUrl)
			.resizable(resizingMode: .stretch)
			.scaledToFill()
	}
	
	private var title: some View {
		VStack(alignment: .center) {
			Text(audioPlayer.title ?? "--")
				.font(Font.ralewayExtraBold(ofSize: 24))
			Text(audioPlayer.artist ?? "--")
				.font(Font.interMedium(ofSize: 14))
		}
	}
	
	private var playbackTimeBinding: Binding<Float> {
		Binding<Float>(get: {
			Float(audioPlayer.currentTime ?? 0)
		}, set: { playbackTime in
			audioPlayer.seek(toTime: Double(playbackTime))
		})
	}
	
	fileprivate var controls: some View {
		VStack(spacing: 0) {
			if let duration = audioPlayer.duration, duration > 0 {
				Slider(value: playbackTimeBinding, in: 0.0...Float(duration))
					.tint(Gradients.loginGradient.gradient)
					.padding(.bottom, -13)
					.zIndex(1)
			}
			
			VStack {
				VStack {
					HStack {
						Text("\(audioPlayer.currentTime.playbackTimeString)")
						Spacer()
						Text("\(audioPlayer.duration.playbackTimeString)")
					}
					.foregroundStyle(try! Color(hex: "8F8F91"))
					.font(Font.inter(ofSize: 12))
					Spacer()
					HStack {
						repeatButton
						Spacer()
						prevButton
						PlayButton().padding([.trailing, .leading])
						nextButton
						Spacer()
						shuffleButton
					}
				}
			}
			.padding()
			.background(Color.black)
			.clipShape(UnevenRoundedRectangle(cornerRadii: RectangleCornerRadii(bottomLeading: 8, bottomTrailing: 8), style: .continuous))
			.frame(height: 108)
		}
	}
	
	private var shuffleButton: some View {
		Button {
			audioPlayer.shuffle.toggle()
		} label: {
			if audioPlayer.shuffle {
				Asset.Media.PlayerIcons.shuffleSelected()
			} else {
				Asset.Media.PlayerIcons.shuffle()
			}
		}
		.tint(audioPlayer.shuffle ? NEWMColor.pink() : .white)
	}
	
	private var prevButton: some View {
		Button {
			audioPlayer.prev()
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

struct NowPlayingView_Previews: PreviewProvider {
	static var previews: some View {
		AudioPlayerModule.shared.registerAllServices()
		@InjectedObject var audioPlayer: VLCAudioPlayer
		return Group {
			NowPlayingView()
				.preferredColorScheme(.dark)
			NowPlayingView().controls
		}.padding()
	}
}

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
