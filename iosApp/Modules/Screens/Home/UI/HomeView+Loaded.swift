import Foundation
import SwiftUI
import SharedUI
import shared
import ModuleLinker
import Resolver

extension HomeView {
	struct LoadedView: View {
		@State private var shouldShowGreeting: Bool = true
		@Binding private var route: HomeRoute?
		private let uiModel: HomeViewUIModel
		
		@State private var greetingTimer: Timer?
		
		init(uiModel: HomeViewUIModel, route: Binding<HomeRoute?>) {
			self.uiModel = uiModel
			self._route = route
		}
		
		var body: some View {
			ScrollView {
				titleSection
				VStack(spacing: 36) {
					ThisWeekSection(uiModel.thisWeekSection)
					recentlyPlayedSection
					justReleasedSection
					moreOfWhatYouLikeSection
					newmArtistsSection
					mostPopularThisWeekSection
				}
			}
			.onAppear {
				greetingTimer = Timer.scheduledTimer(withTimeInterval: 3, repeats: false) { _ in
					withAnimation {
						shouldShowGreeting = false
					}
				}
			}
		}
		
		private var newmArtistsSection: some View {
			HorizontalScrollingGridView(uiModel.newmArtistsSection)
		}
		
		private var mostPopularThisWeekSection: some View {
			HorizontalStackSection(uiModel.mostPopularThisWeek, content: BigCell.init)
		}
		
		private var recentlyPlayedSection: some View {
			HorizontalStackSection(uiModel.recentlyPlayedSection, content: BigCell.init)
		}
		
		private var moreOfWhatYouLikeSection: some View {
			HorizontalStackSection(uiModel.moreOfWhatYouLikeSection, content: BigCell.init)
		}
		
		private var justReleasedSection: some View {
			HorizontalStackSection(uiModel.justReleasedSection, content: BigCell.init)
		}
		
		@ViewBuilder
		private var titleSection: some View {
			TitleSection(isGreeting: shouldShowGreeting,
						 title: shouldShowGreeting ? uiModel.greeting.title : uiModel.title.title,
						 profilePic: shouldShowGreeting ? uiModel.greeting.profilePic : uiModel.title.profilePic,
						 gradient: uiModel.title.gradientHexColors)
				.padding(.bottom, 41)
			//TODO: THIS ANIMATION ISN'T WORKING
				.transition(.opacity.animation(.easeInOut(duration: 1.0)))
		}
	}
}

struct HomeViewLoaded_Previews: PreviewProvider {
	static var previews: some View {
		return HomeView.LoadedView(
			uiModel: MockHomeViewUIModelProvider.mockUIModel(
				actionHandler: MockHomeActionHandler()
			),
			route: .constant(nil)
		)
		.preferredColorScheme(.dark)
	}
}
