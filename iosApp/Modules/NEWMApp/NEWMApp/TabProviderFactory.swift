import TabBar
import Home
import Wallet
import SwiftUI

struct TabProviderFactory {
	func makeTabProvider(for tab: NEWMAppViewModel.Tab) -> TabViewProvider {
		switch tab {
		case .home:
			return TabViewProvider(image: Image(NEWMAppViewModel.Tab.home), tabName: NEWMAppViewModel.Tab.home.description) {
				HomeView().erased
			}
		case .wallet:
			return TabViewProvider(image: Image(NEWMAppViewModel.Tab.wallet), tabName: NEWMAppViewModel.Tab.wallet.description) {
				WalletView().erased
			}
		default:
			//TODO:
			fatalError()
		}
	}
}
