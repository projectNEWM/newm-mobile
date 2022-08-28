import SwiftUI
import ModuleLinker
import Resolver

public struct PlaylistListView: View {
	@Injected private var viewModel: PlaylistListViewModel
		
	public var body: some View {
		ScrollView(.vertical, showsIndicators: false) {
			VStack {
				ForEach(viewModel.playlists) { playlist in
					PlaylistListViewCell(playlist: playlist)
						.padding(5)
						.padding([.leading, .trailing], 5)
				}
			}
		}
		.frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
		.background(Color.black)
	}
}

struct PlaylistListView_Previews: PreviewProvider {
	static var previews: some View {
		PlaylistListView()
	}
}
