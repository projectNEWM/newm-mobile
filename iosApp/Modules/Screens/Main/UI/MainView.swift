import SwiftUI
import Resolver
import ModuleLinker
import TabBar
import AudioPlayer
import SharedUI

public struct MainView: View {
	@StateObject var viewModel = MainViewModel()
	
	@Injected private var tabProviders: [TabViewProvider]
	@Injected private var loginViewProvider: LoginViewProviding
	@Injected private var miniPlayerViewProvider: MiniNowPlayingViewProviding
	@Injected private var nowPlayingViewProvider: NowPlayingViewProviding
	@Injected private var audioPlayer: AudioPlayer
	@InjectedObject private var audioPlayerPublisher: AudioPlayerPublisher
		
	@State var route: MainViewRoute?
	
	public var body: some View {
		GeometryReader { geometry in
			if viewModel.shouldShowLogin {
				loginViewProvider.loginView().transition(.move(edge: .bottom))
			} else {
				TabBar(tabProviders: tabProviders, bottomPadding: miniPlayerHeight)
					.preferredColorScheme(.dark)
					.sheet(isPresented: isPresent($route), onDismiss: { route = nil }) {
						sheetView
					}
					.overlay {
						Spacer()
						miniPlayerView
							.offset(x: 0, y: -geometry.safeAreaInsets.bottom)
							.transition(.move(edge: .bottom))
							.animation(.easeInOut, value: audioPlayer.currentItem)
					}
					.transition(.move(edge: .bottom))
			}
		}
		.animation(.easeInOut, value: viewModel.shouldShowLogin)
	}
	
	@ViewBuilder
	private var miniPlayerView: some View {
		if audioPlayer.currentItem == nil {
			EmptyView()
		} else {
			VStack {
				Spacer()
				miniPlayerViewProvider.miniNowPlayingView()
					.onTapGesture {
						route = .nowPlaying
					}
					.padding(.bottom)
			}
		}
	}
	
	private var miniPlayerHeight: CGFloat {
		audioPlayer.currentItem == nil ? 0 : 50
	}
	
	@ViewBuilder
	private var sheetView: some View {
		switch route {
		case .nowPlaying: nowPlayingViewProvider.nowPlayingView()
		default: EmptyView()
		}
	}
}

struct MainView_Previews: PreviewProvider {
	static var previews: some View {
		MainModule.shared.registerAllServices()
		AudioPlayerModule.shared.registerAllServices()
		return MainView()
	}
}

extension MainViewRoute: Identifiable {
	var id: Self { self }
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
