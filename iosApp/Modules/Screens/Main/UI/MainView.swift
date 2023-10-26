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
	
	public var body: some View {
		GeometryReader { geometry in
			if viewModel.shouldShowLogin {
				loginViewProvider.loginView().transition(.move(edge: .bottom))
			} else {
				TabBar(tabProviders: tabProviders, bottomPadding: miniPlayerHeight)
					.preferredColorScheme(.dark)
					.sheet(isPresented: .constant(route != nil), onDismiss: { route = nil }) {
						sheetView
					}
					.overlay {
						miniPlayerView
							.offset(x: 0, y: -geometry.safeAreaInsets.bottom)
							.transition(.move(edge: .bottom))
							.animation(.easeInOut, value: audioPlayer.song)
					}
					.transition(.move(edge: .bottom))
			}
		}
		.animation(.easeInOut, value: viewModel.shouldShowLogin)
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
