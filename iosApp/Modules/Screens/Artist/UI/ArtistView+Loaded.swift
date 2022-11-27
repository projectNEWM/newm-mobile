import Foundation
import SwiftUI
import SharedUI

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
						HeaderImageSection(uiModel.headerImageSection)
						artistImage
						HStack {
							SupportButton.followButton()
							SupportButton.supportButton()
						}
						//					BigCellSection(uiModel.topSongsSection, actionHandler: actionHandler.songTapped)
						TrackSection(uiModel.trackSection, actionHandler: actionHandler.songTapped)
						//					BigCellSection(uiModel.albumSection, actionHandler: actionHandler.albumTapped)
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
						HeaderImageSection(uiModel.headerImageSection)
						artistImage
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
		
		private var artistImage: some View {
			AsyncImage(url: uiModel.profileImage)
				.circle(size: 128)
				.padding(.top, -(sectionSpacing+artistImageSize/2))
//			{ phase in
//				switch phase {
//				case .success(let image):
//					image.circleImage(size: 128)
//				case .failure(let error):
//					//TODO: LOG
//					Text(error.localizedDescription)
//				case .empty
//				}
//			}
		}
	}
}

struct ArtistView_Preview: PreviewProvider {
	static var previews: some View {
		NavigationView {
			NavigationLink("", destination: {
				ArtistView.LoadedView(actionHandler: ArtistViewModel(),
									  uiModel: try! MockArtistViewUIModelProviding().getModel(artistId: "1"),
									  route: .constant(nil))
			}(), isActive: .constant(true))
		}
		.preferredColorScheme(.dark)
	}
}
