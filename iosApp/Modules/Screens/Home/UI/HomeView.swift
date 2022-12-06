import SwiftUI
import ModuleLinker
import Resolver
import Artist
import SharedUI

struct HomeView: View {
	@StateObject private var viewModel = HomeViewModel()

	public init() {}
	
	public var body: some View {
		NavigationView {
			switch viewModel.state {
			case .loading:
				ProgressView()
			case .loaded(let uiModel):
				LoadedView(uiModel: uiModel, route: $viewModel.route)
			case .error:
				Text("Error")
			}
		}
		.navigationBarTitleDisplayMode(.inline)
	}
}

struct HomeView_Previews: PreviewProvider {
	static var previews: some View {
		//is this doing anything?
//		Resolver.root = .mock
		return HomeView()
			.preferredColorScheme(.dark)
	}
}
