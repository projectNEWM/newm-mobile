import Foundation
import SwiftUI
import SharedUI

extension ArtistView {
	struct LoadedView: View {
		@Binding private var route: ArtistRoute?
		@Environment(\.presentationMode) var presentationMode
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
						SupportButtonsModel(uiModel.followSection)
						SupportButtonsModel(uiModel.supportSection)
					}
//					BigCellSection(uiModel.topSongsSection, actionHandler: actionHandler.songTapped)
					TrackSection(uiModel.trackSection, actionHandler: actionHandler.songTapped)
//					BigCellSection(uiModel.albumSection, actionHandler: actionHandler.albumTapped)
				}
			}
			.navigationBarTitle("J-ROC", displayMode: .inline)
			.navigationBarBackButtonHidden()
//			.toolbar {
//				ToolbarItem(placement: .navigationBarLeading) {
//					Button(action: {
//						self.presentationMode.wrappedValue.dismiss()
//					}) {
//						HStack {
//							Image("Back Button")
//						}
//					}
//				}
//				ToolbarItem(placement: .navigationBarTrailing) {
//					Button(action: {
//						//TODO: add button action
//					}) {
//						Image(systemName: "ellipsis")
//							.rotationEffect(Angle(degrees: 90))
//							.foregroundColor(.white)
//					}
//				}
//			}
		}
	}
}

struct ArtistView_Preview: PreviewProvider {
	static var previews: some View {
		//is this doing anything?
//        Resolver.root = .mock
		return ArtistView()
			.preferredColorScheme(.dark)
	}
}
