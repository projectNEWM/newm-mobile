import Foundation
import SwiftUI
import SharedUI

extension LibraryView {
    struct LoadedView: View {
        @Binding private var route: LibraryRoute?
        private let actionHandler: LibraryViewActionHandling
        private let uiModel: LibraryViewUIModel
        
        init(actionHandler: LibraryViewActionHandling, uiModel: LibraryViewUIModel, route: Binding<LibraryRoute?>) {
            self.actionHandler = actionHandler
            self.uiModel = uiModel
            self._route = route
        }
        
        var body: some View {
            ScrollView {
				titleSection
                VStack(spacing: 36) {
					HorizontalStackSection(uiModel.recentlyPlayedSection, content: BigCell.init)
                    PlaylistsSection(uiModel.yourPlaylistsSection, actionHandler: actionHandler.playlistTapped)
					HorizontalStackSection(uiModel.likedSongsSection, content: BigCell.init)
                }
            }
        }
        
        private var titleSection: some View {
			TitleSection(isGreeting: false,
						 title: uiModel.title.title,
						 gradient: uiModel.title.gradientColors)
                    .padding(.bottom, 41)
        }
    }
}

struct LibraryView_Preview: PreviewProvider {
    static var previews: some View {
        LibraryView()
            .preferredColorScheme(.dark)
    }
}
