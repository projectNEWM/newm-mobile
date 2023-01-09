import Foundation
import Resolver
import ModuleLinker
import SwiftUI
import SharedUI

public struct WalletModule: ModuleProtocol {
	public static let shared = WalletModule()
	
	public func registerAllServices() {
		Resolver.register { self as WalletViewProviding }
	}
}

extension WalletModule: WalletViewProviding {
	public func walletView() -> AnyView {
		WalletView().erased
	}
}

#if DEBUG
extension WalletModule {
	public func registerAllMockedServices(mockResolver: Resolver) {
		mockResolver.register {
			WalletViewModel(
				currencyPicker: WalletViewModel.CurrencyPicker(title: "SELECT MAIN CURRENCY",
															   options: Currency.allCases),
				portfolioSection: PortfolioSectionModel(
					pickerLabel: "EARNINGS PER WEEK",
					songHeaderTitle: "SONG",
					royaltyTitle: "ROYALTIES",
					cells:
						MockData.songs.prefix(10)
						.map {
							($0.title, URL(string: $0.image)!, $0.title, "$\(Int(arc4random_uniform(10))).\(Int(arc4random_uniform(10)))\(Int(arc4random_uniform(10)))")
						}
						.map(PortfolioSectionModel.Cell.init)
				), transactionsSection: TransactionsSectionModel(
					sections: zip([
						"TODAY",
						"YESTERDAY",
						"20 SEPTEMBER"
					],
								  [
									[
										TransactionsSectionModel.Cell(image: Asset.Media.priceTag,
																	  title1: "Minting Fee",
																	  title2: "[Piano Paparazzi]",
																	  time: "18:42",
																	  amount: "$1.50"),
										TransactionsSectionModel.Cell(image: Asset.Media.priceTag,
																	  title1: "Minting Fee",
																	  title2: "[Fury in my arms]",
																	  time: "18:10",
																	  amount: "$1.21"),
									],
									[
										TransactionsSectionModel.Cell(image: Asset.Media.checkmark,
																	  title1: "Royalties claimed",
																	  time: "18:42",
																	  amount: "$1.50"),
										TransactionsSectionModel.Cell(image: Asset.Media.checkmark,
																	  title1: "Royalties claimed",
																	  time: "18:10",
																	  amount: "$1.21"),
									],
									[
										TransactionsSectionModel.Cell(image: Asset.Media.priceTag,
																	  title1: "Minting Fee",
																	  title2: "[The Elder Story]",
																	  time: "18:42",
																	  amount: "$1.50"),
									]
								  ]
								 ).map(TransactionsSectionModel.Section.init)
				), thisWeekSection: ThisWeekSectionModel(title: "THIS WEEK IN REVIEW", cells: MockData.thisWeekCells),
				yourFundsSection: YourFundsSectionModel()
			)
		}
	}
}
#endif
