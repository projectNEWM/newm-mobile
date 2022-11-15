import SwiftUI
import TabBar
import Resolver
import ModuleLinker
import AudioPlayer

public struct MainView: View {
	@StateObject var viewModel = MainViewModel()
	
	@Injected private var tabProviders: [TabViewProvider]
	@Injected private var loginViewProvider: LoginViewProviding
	@Injected private var miniPlayerViewProvider: MinimizedNowPlayingViewProviding
	@Injected private var nowPlayingViewProvider: NowPlayingViewProviding
	@ObservedObject private var audioPlayer: AudioPlayerImpl = AudioPlayerImpl.shared
	
	@State var route: MainViewRoute?
	
	public init() {}
	
	public var body: some View {
		TabBar(tabProviders: tabProviders)
			.preferredColorScheme(.dark)
			.fullScreenCover(isPresented: $viewModel.shouldShowLogin) { } content: {
				loginViewProvider.loginView()
			}
			.sheet(isPresented: .constant(route != nil), onDismiss: { route = nil }) {
				sheetView
			}
			.overlay {
				GeometryReader { geometry in
					miniPlayerView.offset(x: 0, y: -(geometry.safeAreaInsets.bottom + 5))
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
				miniPlayerViewProvider.minimizedNowPlayingView()
					.onTapGesture {
						route = .nowPlaying
					}
			}
		}
	}
	
	@ViewBuilder
	private var nowPlayingView: some View {
		switch route {
		case .nowPlaying:
			nowPlayingViewProvider.nowPlayingView()
		default:
			EmptyView()
		}
	}
	
	@ViewBuilder
	private var sheetView: some View {
		switch route {
		case .nowPlaying: nowPlayingView
		default: EmptyView()
		}
	}
}

import SharedUI
struct MainView_Previews: PreviewProvider {
	@ObservedObject static private var audioPlayer: AudioPlayerImpl = AudioPlayerImpl.shared

	static var previews: some View {
		audioPlayer.song = MockData.songs.first!
		return MainView()
	}
}

extension MainViewRoute: Identifiable {
	var id: ObjectIdentifier { "\(self)".objectIdentifier }
}
