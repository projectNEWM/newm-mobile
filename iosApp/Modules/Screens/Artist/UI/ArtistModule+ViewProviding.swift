import Foundation
import SwiftUI
import ModuleLinker

extension ArtistModule: ArtistViewProviding {
	public func artistView(id: String) -> AnyView {
		ArtistView(artistId: id).erased
	}
}
