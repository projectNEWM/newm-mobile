import SwiftUI
import ModuleLinker
import Resolver
import SharedUI
import Colors

public struct WalletView: View {
	@InjectedObject private var viewModel: WalletViewModel
	@State private var showCurrencyPicker = false
	
	public var body: some View {
		ScrollView {
			LazyVStack(spacing: 24) {
				title
			}
		}
	}
	
	@ViewBuilder
	private var title: some View {
		HStack {
			Text(viewModel.titleSection.title)
				.style(.screenTitle(viewModel.titleSection.gradient))
			Spacer()
			currencyPicker
		}
		.addSidePadding()
	}
	
	@ViewBuilder
	private var currencyPicker: some View {
		Button(action: {
			showCurrencyPicker = true
		}, label: {
			HStack(spacing: 2) {
				Text(viewModel.selectedCurrency.symbol).font(.inter(ofSize: 12).bold())
				Asset.Media.arrowSmallDown.swiftUIImage
			}
			.padding(.leading, 18)
			.padding(.trailing, 12)
			.padding([.top, .bottom], 8)
			.background(NEWMColor.grey600())
			.cornerRadius(1000)
		})
		.foregroundColor(.white)
		.sheet(isPresented: $showCurrencyPicker) {
			PickerSheet(
				options: viewModel.currencyPicker.options,
				selectedOption: $viewModel.selectedCurrency,
				buttonBackground: viewModel.gradient,
				title: viewModel.currencyPicker.title,
				onSelect: { showCurrencyPicker = false }
			)
			.presentationDetents([.medium, .large])
			.presentationDragIndicator(.hidden)
			.background(Color.black)
		}
	}
}

extension Currency: CustomStringConvertible {
	public var description: String {
		"\(symbol) - \(title)"
	}
}

struct WalletView_Previews: PreviewProvider {
	static var previews: some View {
		Group {
			WalletView()
		}
		.preferredColorScheme(.dark)
	}
}
