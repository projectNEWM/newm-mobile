import Foundation
import SwiftUI
import ModuleLinker

extension MarketplaceModule: MarketplaceViewProviding {
	public func marketplaceView() -> AnyView {
		MarketplaceView().erased
	}
}
