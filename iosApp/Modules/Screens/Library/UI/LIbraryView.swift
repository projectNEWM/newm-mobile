import Foundation
import SwiftUI
import Resolver
import SharedUI

struct LibraryView: View {
    @StateObject private var viewModel = LibraryViewModel()

    public init() {}
    
    public var body: some View {
        NavigationView {
            switch viewModel.state {
            case .loading:
                ProgressView()
            case .loaded(let (uiModel, actionHandler)):
                LoadedView(actionHandler: actionHandler, uiModel: uiModel, route: $viewModel.route)
            case .error:
                Text("Error")
            }
        }
        .navigationBarTitleDisplayMode(.inline)
    }
}

//struct LibraryView_Previews: PreviewProvider {
//    static var previews: some View {
//        //is this doing anything?
//        Resolver.root = .mock
//        return LibraryView()
//            .preferredColorScheme(.dark)
//    }
//}
