import Foundation
import Colors
import ModuleLinker
import SharedUI

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

struct TransactionsSectionModel {
	struct Cell {
		init(image: ImageAsset, title1: String, title2: String? = nil, time: String, amount: String) {
			self.image = image
			self.title1 = title1
			self.title2 = title2
			self.time = time
			self.amount = amount
		}
		
		let image: ImageAsset
		let title1: String
		let title2: String?
		let time: String
		let amount: String
	}
	
	struct Section {
		let title: String
		let cells: [Cell]
	}
	
	let sections: [Section]
}

struct YourFundsSectionModel {
	let title = "YOUR FUNDS"
	let funds = "$1,140.03"
	let sendTitle = "Send"
	let receiveTitle = "Receive"
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
	let transactionsSection: TransactionsSectionModel
	let thisWeekSection: ThisWeekSectionModel
	static let transactionTitle: String = "TRANSACTIONS"
	static let portfolioTitle: String = "PORTFOLIO"
	let yourFundsSection: YourFundsSectionModel
	
	init(selectedCurrency: Currency = Currency.allCases.first!, currencyPicker: WalletViewModel.CurrencyPicker, portfolioSection: PortfolioSectionModel, transactionsSection: TransactionsSectionModel, thisWeekSection: ThisWeekSectionModel, yourFundsSection: YourFundsSectionModel) {
		self.selectedCurrency = selectedCurrency
		self.currencyPicker = currencyPicker
		self.portfolioSection = portfolioSection
		self.transactionsSection = transactionsSection
		self.thisWeekSection = thisWeekSection
		self.yourFundsSection = yourFundsSection
	}
}
