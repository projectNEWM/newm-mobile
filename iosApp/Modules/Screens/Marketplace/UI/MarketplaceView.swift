import SwiftUI
import SharedUI
import Resolver
import Colors

struct MarketplaceView: View {
	@Injected private var viewModel: MarketplaceViewModel
	
    var body: some View {
		ScrollView {
			LazyVStack(spacing: 32) {
				TitleSection(model: viewModel.titleSection)
				RadioPicker(options: viewModel.allCategories)
				HorizontalStackSection(viewModel.trendingSongs, content: TrendingSongCell.init)
			}
		}
    }
}

struct MarketplaceView_Previews: PreviewProvider {
    static var previews: some View {
        MarketplaceView()
			.preferredColorScheme(.dark)
    }
}
