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
							("\(MockData.songs.firstIndex(of: $0)!)", URL(string: $0.image)!, $0.title, "$2.34")
						}
						.map(PortfolioSectionModel.Cell.init)
				)
			)
		}
	}
}
#endif
