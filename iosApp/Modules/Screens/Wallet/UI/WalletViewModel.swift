import Foundation
import Colors
import ModuleLinker

struct PortfolioSectionModel {
	struct Cell {
		let id: String
		let image: URL
		let title: String
		let price: String
	}
	
	let pickerLabel: String
	let songHeaderTitle: String
	let royaltyTitle: String
	let cells: [Cell]
}

class WalletViewModel: ObservableObject {
	struct CurrencyPicker {
		let title: String
		let options: [Currency]
	}
	
	let titleSection: WalletTitleSectionModel = WalletTitleSectionModel(title: "WALLET", gradient: Gradients.walletGradient)
	@Published var selectedCurrency: Currency
	let currencyPicker: CurrencyPicker
	let gradient = Gradients.walletGradient
	let portfolioSection: PortfolioSectionModel
	
	init(selectedCurrency: Currency = Currency.allCases.first!, currencyPicker: WalletViewModel.CurrencyPicker, portfolioSection: PortfolioSectionModel) {
		self.selectedCurrency = selectedCurrency
		self.currencyPicker = currencyPicker
		self.portfolioSection = portfolioSection
	}
}
