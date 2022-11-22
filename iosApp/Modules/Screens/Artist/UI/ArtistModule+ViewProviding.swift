import Foundation
import SwiftUI
import ModuleLinker

public protocol ArtistViewProviding {
	func artistView(id: String) -> AnyView
}

extension ArtistModule: ArtistViewProviding {
	public func artistView(id: String) -> AnyView {
		ArtistView().erased
	}
}
