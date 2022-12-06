import Foundation
import SwiftUI
import SharedUI
import shared

extension ArtistView {
	struct LoadedView: View {
		@Binding private var route: ArtistRoute?
		private let actionHandler: ArtistViewActionHandling
		private let uiModel: ArtistViewUIModel
		
		private let sectionSpacing: CGFloat = 36
		private let artistImageSize: CGFloat = 128
		
		init(actionHandler: ArtistViewActionHandling, uiModel: ArtistViewUIModel, route: Binding<ArtistRoute?>) {
			self.actionHandler = actionHandler
			self.uiModel = uiModel
			self._route = route
		}
		
		var body: some View {
			if #available(iOS 16.0, *) {
				ScrollView {
					VStack(spacing: sectionSpacing) {
						HeaderImageSection(uiModel.headerImageUrl)
						artistImage
						HStack(spacing: 12) {
							SupportButton.followButton()
							SupportButton.supportButton()
						}
						topSongs
						TrackSection(uiModel.trackSection)
					}
					.padding(.top, 100)
				}
				.backButton()
				.toolbarBackground(.visible, for: .navigationBar)
				.toolbarBackground(Color.black, for: .navigationBar)
				.navigationTitle(uiModel.title)
			} else {
				//TODO: figure out how to do this in pre-ios16
				ScrollView {
					VStack(spacing: sectionSpacing) {
						HeaderImageSection(uiModel.headerImageUrl)
						artistImage
						HStack {
							SupportButton.followButton()
							SupportButton.supportButton()
						}
						TrackSection(uiModel.trackSection)
					}
				}
				.backButton()
			}
		}
		
		private var artistImage: some View {
			AsyncImage(url: uiModel.profileImage)
				.circle(size: 128)
				.padding(.top, -(sectionSpacing+artistImageSize/2))
		}
		
		private var topSongs: some View {
			//TODO: localize
			HorizontalStackSection(uiModel.topSongs, content: BigCell.init)
		}
	}
}

struct ArtistView_Preview: PreviewProvider {
	private class MockArtistActionHandler: ArtistViewActionHandling {
		func songTapped(id: String) {}
		func albumTapped(id: String) {}
		func songPlayingTapped(id: String) {}
	}
	static var previews: some View {
		ArtistView.LoadedView(actionHandler: ArtistViewModel(artistId: MockData.artists.first!.id),
							  uiModel: try! MockArtistViewUIModelProviding().getModel(artist: MockData.artists.first!,
																					  actionHandler: MockArtistActionHandler()),
							  route: .constant(nil))
		.preferredColorScheme(.dark)
	}
}
