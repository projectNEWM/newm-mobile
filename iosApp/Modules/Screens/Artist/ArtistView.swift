import SwiftUI
import ModuleLinker
import Resolver

public protocol ArtistViewProviding {
	func artistView(id: String) -> AnyView
}

struct ArtistView: DataView {
	var id: String
//	@Injected private var viewModel: ArtistViewModel

    var body: some View {
		Text("Artist")
    }
}

struct ArtistView_Previews: PreviewProvider {
    static var previews: some View {
		return ArtistView(id: "1")
    }
}
