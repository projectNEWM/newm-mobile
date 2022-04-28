import SwiftUI
import ModuleLinker
import Resolver

struct ArtistView: DataView {
	var id: String
//	@Injected private var viewModel: ArtistViewModel

    var body: some View {
		Text("Artist")
    }
}

//struct ArtistView_Previews: PreviewProvider {
//    static var previews: some View {
//		ArtistView(artist: DummyData.makeArtist(name: "David Bowie"))
//    }
//}
