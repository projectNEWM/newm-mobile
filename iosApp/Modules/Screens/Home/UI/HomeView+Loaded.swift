import Foundation
import SwiftUI
import SharedUI
import shared
import ModuleLinker
import Resolver
import AudioPlayer

extension HomeView {
	struct LoadedView: View {
		@State private var shouldShowGreeting: Bool = true
		@Binding private var route: HomeRoute?
		private let actionHandler: HomeViewActionHandling
		private let uiModel: HomeViewUIModel
		
		@Injected private var audioPlayer: AudioPlayerImpl
		
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
					recentlyPlayingSection
					justReleasedSection
					moreOfWhatYouLikeSection
					newmArtistsSection
					mostPopularThisWeekSection
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
		
		private func horizontalBigArtistSection(title: String, artists: [Artist]) -> some View {
			HorizontalStackSection(title: title, models: artists) { artist in
				BigArtistCell(model: BigCellViewModel(artist: artist))
					.onTapGesture {
						route = .artist(id: artist.id)
					}
			}
		}
		
		private var newmArtistsSection: some View {
			HorizontalScrollingGridView(uiModel.newmArtistsSection) { artistId in
				route = .artist(id: artistId)
			}
		}
		
		private var mostPopularThisWeekSection: some View {
			horizontalBigArtistSection(title: uiModel.mostPopularThisWeekTitle, artists: uiModel.mostPopularThisWeek)
		}
		
		@ViewBuilder
		private var recentlyPlayingSection: some View {
			HorizontalStackSection(title: uiModel.recentlyPlayedTitle, models: uiModel.recentlyPlayedSection) { song in
				BigArtistCell(model: BigCellViewModel(song: song))
					.onTapGesture {
						audioPlayer.song = song
						audioPlayer.playbackInfo.isPlaying = true
					}
			}
		}
		
		private var moreOfWhatYouLikeSection: some View {
			horizontalBigArtistSection(title: uiModel.moreOfWhatYouLikeTitle, artists: uiModel.moreOfWhatYouLikeSection)
		}
		
		private var justReleasedSection: some View {
			horizontalBigArtistSection(title: uiModel.justReleasedTitle, artists: uiModel.justReleasedSection)
		}
		
		private var titleSection: some View {
			TitleSection(model: shouldShowGreeting ? uiModel.greeting : uiModel.title)
				.padding(.bottom, 41)
			//TODO: THIS ANIMATION ISN'T WORKING
				.transition(.opacity.animation(.easeInOut(duration: 1.0)))
		}
	}
}

extension Song: Identifiable {}
extension Artist: Identifiable {}

struct HomeViewLoaded_Previews: PreviewProvider {
	static var previews: some View {
		let vm = HomeViewModel()
		return HomeView.LoadedView(actionHandler: vm,
								   uiModel: MockHomeViewUIModelProvider.mockUIModel,
								   route: .constant(nil))
		.preferredColorScheme(.dark)
	}
}
