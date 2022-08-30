import Foundation
import SwiftUI
import SharedUI

extension HomeView {
	struct LoadedView: View {
		@State private var shouldShowGreeting: Bool = true
		@Binding private var route: HomeRoute?
		private let actionHandler: HomeViewActionHandling
		private let uiModel: HomeViewUIModel
		
		init(actionHandler: HomeViewActionHandling, uiModel: HomeViewUIModel, route: Binding<HomeRoute?>) {
			self.actionHandler = actionHandler
			self.uiModel = uiModel
			self._route = route
		}
		
		var body: some View {
			ScrollView {
				titleSection
				VStack(spacing: 36) {
					ThisWeekSection(uiModel.thisWeekSection)
					BigCellSection(uiModel.recentlyPlayedSection, actionHandler: actionHandler.songTapped)
					BigCellSection(uiModel.justReleasedSection, actionHandler: actionHandler.artistTapped)
					BigCellSection(uiModel.moreOfWhatYouLikeSection, actionHandler: actionHandler.artistTapped)
					ArtistsSection(uiModel.newmArtistsSection, actionHandler: actionHandler.artistTapped)
					BigCellSection(uiModel.mostPopularThisWeek, actionHandler: actionHandler.artistTapped)
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
			.links(Links(route: $route))
		}
		
		private var titleSection: some View {
			TitleSection(model: shouldShowGreeting ? uiModel.greeting : uiModel.title)
				.padding(.bottom, 41)
			//TODO: THIS ANIMATION ISN'T WORKING
				.transition(.opacity.animation(.easeInOut(duration: 1.0)))
		}
	}
}
