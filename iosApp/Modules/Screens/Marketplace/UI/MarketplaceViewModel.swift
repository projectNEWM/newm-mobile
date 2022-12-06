import Foundation
import SharedUI
import ModuleLinker
import Colors

class MarketplaceViewModel: ObservableObject {
	@Published var titleSection = TitleSectionModel(title: "MARKETPLACE", gradientColors: ColorAsset.marketplaceGradient.map(\.color))
}
