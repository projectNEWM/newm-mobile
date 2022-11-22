import SwiftUI
import ModuleLinker
import Resolver


struct ArtistView: View {
	@StateObject private var viewModel = ArtistViewModel()
	
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
	}
}

struct ArtistView_Previews: PreviewProvider {
	static var previews: some View {
		return ArtistView()
	}
}
