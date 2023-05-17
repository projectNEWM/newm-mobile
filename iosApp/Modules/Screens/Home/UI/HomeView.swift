import SwiftUI
import ModuleLinker
import Resolver
import Artist
import SharedUI

struct HomeView: View {
	@StateObject var viewModel = HomeViewModel()
	@State private var shouldShowGreeting: Bool = true
	
	@State private var greetingTimer: Timer?
	
	@Injected var artistViewProvider: ArtistViewProviding
	
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
			.onChange(of: viewModel.loginManager.userIsLoggedIn) { _ in
				resetGreetingTimer()
			}
			.onAppear(perform: resetGreetingTimer)
			.navigationDestination(for: HomeRoute.self) { route in
				switch route {
				case .artist(let id):
					artistViewProvider.artistView(id: id)
				default:
					EmptyView()
				}
			}
		}
		.navigationBarTitleDisplayMode(.inline)
	}
	
	private func resetGreetingTimer() {
		shouldShowGreeting = true
		greetingTimer?.invalidate()
		greetingTimer = Timer.scheduledTimer(withTimeInterval: 3, repeats: false) { _ in
			withAnimation {
				shouldShowGreeting = false
			}
		}
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
		return TitleSection(isGreeting: shouldShowGreeting,
							title: shouldShowGreeting ? uiModel.greeting.title : uiModel.title.title,
							profilePic: shouldShowGreeting ? uiModel.greeting.profilePic : uiModel.title.profilePic,
							gradient: uiModel.title.gradientHexColors)
		.padding(.bottom, 41)
		.transition(.opacity)
		.animation(.easeInOut(duration: 1.0), value: shouldShowGreeting)
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
