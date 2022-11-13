import SwiftUI
import TabBar
import Resolver
import ModuleLinker
import AudioPlayer

public struct MainView: View {
	@StateObject var viewModel = MainViewModel()
	
	@Injected private var tabProviders: [TabViewProvider]
	@Injected private var loginViewProvider: LoginViewProviding
	@Injected private var playerViewProvider: MinimizedNowPlayingViewProviding
	@Injected private var songPlayingViewProvider: SongPlayingViewProviding
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
				switch route {
				case .nowPlaying: nowPlayingView
				default: EmptyView()
				}
			}
			.overlay(playerView)
	}
	
	@ViewBuilder
	private var playerView: some View {
		if audioPlayer.song == nil {
			EmptyView()
		} else {
			VStack {
				Spacer()
				playerViewProvider.minimizedNowPlayingView()
					.padding(.bottom, 44)
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
			songPlayingViewProvider.songPlayingView()
		default:
			EmptyView()
		}
	}
}

struct MainView_Previews: PreviewProvider {
	static var previews: some View {
		MainView()
	}
}

extension MainViewRoute: Identifiable {
	var id: ObjectIdentifier { "\(self)".objectIdentifier }
}
