import SwiftUI
import SharedUI
import Resolver
import Colors

struct MarketplaceView: View {
	@Injected private var viewModel: MarketplaceViewModel
	
    var body: some View {
		ScrollView {
			TitleSection(model: viewModel.titleSection)
		}
    }
}

struct MarketplaceView_Previews: PreviewProvider {
    static var previews: some View {
        MarketplaceView()
			.preferredColorScheme(.dark)
    }
}
