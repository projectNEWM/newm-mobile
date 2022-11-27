import Foundation
import SwiftUI
import SharedUI

extension ArtistView {
	struct LoadedView: View {
		@Binding private var route: ArtistRoute?
		private let actionHandler: ArtistViewActionHandling
		private let uiModel: ArtistViewUIModel
		
		init(actionHandler: ArtistViewActionHandling, uiModel: ArtistViewUIModel, route: Binding<ArtistRoute?>) {
			self.actionHandler = actionHandler
			self.uiModel = uiModel
			self._route = route
		}
		
		var body: some View {
			ScrollView {
				VStack(spacing: 36) {
					HeaderImageSection(uiModel.headerImageSection)
					ProfileImageSection(uiModel.profileImageSection)
					HStack {
						SupportButton.followButton()
						SupportButton.supportButton()
					}
//					BigCellSection(uiModel.topSongsSection, actionHandler: actionHandler.songTapped)
					TrackSection(uiModel.trackSection, actionHandler: actionHandler.songTapped)
//					BigCellSection(uiModel.albumSection, actionHandler: actionHandler.albumTapped)
				}
			}
			.backButton()
		}
	}
}

struct ArtistView_Preview: PreviewProvider {
	static var previews: some View {
		ArtistView()
			.preferredColorScheme(.dark)
	}
}
