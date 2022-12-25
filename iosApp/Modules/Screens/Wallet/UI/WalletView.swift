import SwiftUI
import ModuleLinker
import Resolver
import SharedUI
import Colors

public struct WalletView: View {
	@InjectedObject private var viewModel: WalletViewModel
	@State private var showCurrencyPicker = false
	@State var selectedPortfolioTransactions: String = WalletViewModel.portfolioTitle
	
	public var body: some View {
		ScrollView {
			LazyVStack(spacing: 24) {
				title
				thisWeek
				portfolioAndTransactionsSection
				footerView
			}
		}
	}
}

extension WalletView {
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
	private var thisWeek: some View {
		ThisWeekSection(viewModel.thisWeekSection)
	}
	
	@ViewBuilder
	private var currencyPicker: some View {
		Button(action: {
			showCurrencyPicker = true
		}, label: {
			PickerButton(label: viewModel.selectedCurrency.symbol)
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
	
	@ViewBuilder
	private var portfolioAndTransactionsSection: some View {
		VStack {
			SegmentedRadioPicker(options: [WalletViewModel.portfolioTitle, WalletViewModel.transactionTitle],
								 selectedOption: $selectedPortfolioTransactions,
								 RadioButtonType: SegmentedButton.self,
								 gradient: selectedPortfolioTransactions == WalletViewModel.portfolioTitle ? Gradients.walletGradient : Gradients.libraryGradient)
			.padding(.bottom)
			switch selectedPortfolioTransactions {
			case WalletViewModel.portfolioTitle:
				PortfolioView(model: viewModel.portfolioSection)
			case WalletViewModel.transactionTitle:
				TransactionsView(model: viewModel.transactionsSection)
					.addSidePadding()
			default: fatalError("string wasn't portfolio or transaction")
			}
		}
		.padding(.top)
	}
	
	@ViewBuilder
	private var footerView: some View {
		VStack {
			Text("You're all caught up. 🎉")
				.padding()
				.font(.inter(ofSize: 14))
				.foregroundColor(NEWMColor.grey100.swiftUIColor)
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
