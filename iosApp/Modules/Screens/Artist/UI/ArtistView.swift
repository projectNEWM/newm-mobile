import SwiftUI
import ModuleLinker
import Resolver

struct ArtistView: View {
	@ObservedObject private var viewModel: ArtistViewModel
	
	init(artistId: String) {
		viewModel = ArtistViewModel(artistId: artistId)
	}
	
	public var body: some View {
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

struct ArtistView_Previews: PreviewProvider {
	static var previews: some View {
		return ArtistView(artistId: "1")
	}
}
