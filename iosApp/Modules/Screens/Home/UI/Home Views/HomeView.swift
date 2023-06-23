import SwiftUI
import ModuleLinker
import Resolver
import SharedUI

struct HomeView: View {
	@StateObject fileprivate var viewModel = HomeViewModel()
		
	@Injected private var artistViewProvider: ArtistViewProviding
	
	var body: some View {
		NavigationStack(path: $viewModel.route) {
			ScrollView {
				titleSection
				VStack(spacing: 36) {
					recentlyPlayedSection
					justReleasedSection
					moreOfWhatYouLikeSection
					newmArtistsSection
					mostPopularThisWeekSection
				}
			}
			.navigationDestination(for: HomeRoute.self) { route in
				switch route {
				case .artist(let id):
					artistViewProvider.artistView(id: id).backButton(withToolbar: true)
				case .profile:
					ProfileView().backButton(withToolbar: true)
				default:
					EmptyView()
				}
			}
		}
		.navigationBarTitleDisplayMode(.inline)
	}
	
	private var newmArtistsSection: some View {
		guard case let .loaded(uiModel) = viewModel.state else { return EmptyView().erased }
		return HorizontalScrollingGridView(uiModel.newmArtistsSection).erased
	}
	
	private var mostPopularThisWeekSection: some View {
		guard case let .loaded(uiModel) = viewModel.state else { return EmptyView().erased }
		return HorizontalStackSection(uiModel.mostPopularThisWeek, content: BigCell.init).erased
	}
	
	private var recentlyPlayedSection: some View {
		guard case let .loaded(uiModel) = viewModel.state else { return EmptyView().erased }
		return HorizontalStackSection(uiModel.recentlyPlayedSection, content: BigCell.init).erased
	}
	
	private var moreOfWhatYouLikeSection: some View {
		guard case let .loaded(uiModel) = viewModel.state else { return EmptyView().erased }
		return HorizontalStackSection(uiModel.moreOfWhatYouLikeSection, content: BigCell.init).erased
	}
	
	private var justReleasedSection: some View {
		guard case let .loaded(uiModel) = viewModel.state else { return EmptyView().erased }
		return HorizontalStackSection(uiModel.justReleasedSection, content: BigCell.init).erased
	}
	
	private var titleSection: some View {
		guard case let .loaded(uiModel) = viewModel.state else { return EmptyView().erased }
		return TitleSection(isGreeting: viewModel.shouldShowGreeting,
							title: viewModel.shouldShowGreeting ? uiModel.greeting.title : uiModel.title.title,
							profilePic: viewModel.shouldShowGreeting ? uiModel.greeting.profilePic : uiModel.title.profilePic,
							gradient: uiModel.title.gradientHexColors,
							profileAction: viewModel.profileTapped)
		.padding(.bottom, 41)
		.transition(.opacity)
		.animation(.easeInOut(duration: 1.0), value: viewModel.shouldShowGreeting)
		.erased
	}
}

struct HomeView_Previews: PreviewProvider {
	static var previews: some View {
		let view = HomeView()
		Task {
			view.viewModel.state = try await .loaded(MockHomeViewUIModelProvider(actionHandler: view.viewModel).getModel())
		}
		return view.preferredColorScheme(.dark)
	}
}
