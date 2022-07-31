import SwiftUI
import ModuleLinker
import Resolver
import SwiftUINavigation
import Artist
import SharedUI

struct HomeView: View {
	@InjectedObject private var viewModel: HomeViewModel
	@State private var shouldShowGreeting: Bool = true
	
	public init() {}
	
	public var body: some View {
		NavigationView {
			ZStack {
//				links
				switch viewModel.state {
				case .loading:
					ProgressView().erased
				case .loaded(let (actionHandler, uiModel)):
					allViews(uiModel: uiModel, actionHandler: actionHandler).erased
				case .error:
					Text("Error").erased
				}
			}
			.navigationBarTitleDisplayMode(.inline)
		}
	}
	
//	private var links: some View {
//		func clearLinks(isActive: Bool) {
//			if isActive == false { viewModel.homeRoute = nil }
//		}
//		return ZStack {
//			NavigationLink(unwrapping: $viewModel.homeRoute,
//						   case: /HomeRoute.songPlaying,
//						   destination: { $songId in
//				songPlayingViewProvider.songPlayingView(id: songId)
//			}, onNavigate: clearLinks, label: {})
//
//			NavigationLink(unwrapping: $viewModel.homeRoute,
//						   case: /HomeRoute.artist,
//						   destination: { $artistId in
//				artistViewProvider.artistView(id: artistId)
//			}, onNavigate: clearLinks, label: {})
//
//			NavigationLink(unwrapping: $viewModel.homeRoute,
//						   case: /HomeRoute.playlist,
//						   destination: { $playlistId in
//				playlistViewProvider.playlistView(id: playlistId)
//			}, onNavigate: clearLinks, label: {})
//		}
//	}
	
	private func allViews(uiModel: HomeViewUIModel, actionHandler: HomeViewActionHandler) -> some View {
		ScrollView {
			titleSection(shouldShowGreeting ? uiModel.greeting : uiModel.title)
			//TODO: THIS ANIMATION ISN'T WORKING
				.transition(.opacity.animation(.easeInOut(duration: 1.0)))
			VStack(spacing: 36) {
				ThisWeekSection(uiModel.thisWeekSection)
				BigCellSection(uiModel.recentlyPlayedSection)
				BigCellSection(uiModel.justReleasedSection)
				BigCellSection(uiModel.moreOfWhatYouLikeSection)
				ArtistsSection(uiModel.newmArtistsSection)
				BigCellSection(uiModel.mostPopularThisWeek)
			}
		}
		.onAppear {
			//TODO: THIS ANIMATION ISN'T WORKING
			withAnimation {
				DispatchQueue.main.asyncAfter(deadline: .now() + 3) {
					shouldShowGreeting = false
				}
			}
		}
	}
	
	private func titleSection(_ model: HomeViewTitleSectionModel) -> some View {
		TitleSection(model: model)
			.padding(.bottom, 41)
	}
}

struct HomeView_Previews: PreviewProvider {
	static var previews: some View {
		//is this doing anything?
//		Resolver.root = .mock
		return HomeView()
			.preferredColorScheme(.dark)
	}
}
