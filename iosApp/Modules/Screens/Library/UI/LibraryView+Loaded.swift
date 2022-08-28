import Foundation
import SwiftUI
import SharedUI

extension LibraryView {
    struct LoadedView: View {
        @State private var shouldShowGreeting: Bool = true
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
                    BigCellSection(uiModel.recentlyPlayedSection, actionHandler: actionHandler.songTapped)
                    PlaylistsSection(uiModel.yourPlaylistsSection, actionHandler: actionHandler.songTapped)
                    BigCellSection(uiModel.likedSongsSection, actionHandler: actionHandler.songTapped)
                } 
            }
        }
        
        private var titleSection: some View {
            TitleSection(model: uiModel.title)
                    .padding(.bottom, 41)
                //TODO: THIS ANIMATION ISN'T WORKING
                //                .transition(.opacity.animation(.easeInOut(duration: 1.0)))
        }
    }
}

struct LibraryView_Previews: PreviewProvider {
    static var previews: some View {
        //is this doing anything?
//        Resolver.root = .mock
        return LibraryView()
            .preferredColorScheme(.dark)
    }
}
