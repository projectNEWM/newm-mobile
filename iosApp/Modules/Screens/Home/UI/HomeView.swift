import SwiftUI
import ModuleLinker
import Resolver
import SwiftUINavigation
import Artist
import SharedUI

struct HomeView: View {
	@InjectedObject private var viewModel: HomeViewModel
	
	public init() {}
	
	public var body: some View {
		NavigationView {
			ZStack {
//				links
				switch viewModel.state {
				case .loading:
					ProgressView().erased
				case .loaded(let uiModel):
					allViews(uiModel: uiModel)
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
	
	private func allViews(uiModel: HomeViewUIModel) -> some View {
		ScrollView {
			titleSection(uiModel.titleSectionModel)
			VStack(spacing: 36) {
				ThisWeekSection(uiModel.thisWeekSection)
				BigCellSection(uiModel.recentlyPlayedSection)
				BigCellSection(uiModel.justReleasedSection)
				BigCellSection(uiModel.moreOfWhatYouLikeSection)
				ArtistsSection(uiModel.newmArtistsSection)
				BigCellSection(uiModel.mostPopularThisWeek)
			}
		}
	}
	
	private func titleSection(_ model: HomeViewUIModel.TitleSectionModel) -> some View {
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
