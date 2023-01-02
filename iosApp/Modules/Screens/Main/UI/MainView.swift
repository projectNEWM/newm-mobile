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
	@InjectedObject private var audioPlayer: AudioPlayerImpl
	
	@State var route: MainViewRoute?
	
	public init() {}
	
	public var body: some View {
		GeometryReader { geometry in
			TabBar(tabProviders: tabProviders, bottomPadding: miniPlayerHeight)
				.preferredColorScheme(.dark)
				.fullScreenCover(isPresented: $viewModel.shouldShowLogin) { } content: {
					loginViewProvider.loginView()
				}
				.sheet(isPresented: .constant(route != nil), onDismiss: { route = nil }) {
					sheetView
				}
				.overlay {
					miniPlayerView
						.offset(x: 0, y: -geometry.safeAreaInsets.bottom)
						
				}
		}
	}
	
	@ViewBuilder
	private var miniPlayerView: some View {
		if audioPlayer.song == nil {
			EmptyView()
		} else {
			VStack {
				Spacer()
				miniPlayerViewProvider.miniNowPlayingView()
					.onTapGesture {
						route = .nowPlaying
					}
			}
		}
	}
	
	private var miniPlayerHeight: CGFloat {
		audioPlayer.song == nil ? 0 : 60
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
		MainView()
	}
}

extension MainViewRoute: Identifiable {
	var id: Self { self }
}
