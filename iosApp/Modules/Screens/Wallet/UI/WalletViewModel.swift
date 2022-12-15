import Foundation
import Colors
import ModuleLinker

class WalletViewModel: ObservableObject {
	struct CurrencyPicker {
		let title: String
		let options: [Currency]
	}
	
	let titleSection: WalletTitleSectionModel = WalletTitleSectionModel(title: "WALLET", gradient: Gradients.walletGradient)
	@Published var selectedCurrency: Currency
	let currencyPicker: CurrencyPicker
	let gradient = Gradients.walletGradient
	
	init(selectedCurrency: Currency = Currency.allCases.first!, currencyPicker: WalletViewModel.CurrencyPicker) {
		self.selectedCurrency = selectedCurrency
		self.currencyPicker = currencyPicker
	}
}
