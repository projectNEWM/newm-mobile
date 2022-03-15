import SwiftUI
import TabBar
import Login
import Home
import Wallet

public struct NEWMApp: View {
	@State var viewModel = NEWMAppViewModel()
	@AppStorage("loggedInUser") var loggedInUser: String?
	
	public init() { }
	
	public var body: some View {
		TabBar(tabProviders: [
			homeTabProvider,
			walletTabProvider
		])
			.preferredColorScheme(.dark)
			.fullScreenCover(isPresented: .constant(loggedInUser == nil)) { } content: {
				LoginView()
			}
		//TODO: remove this
			.onReceive(NotificationCenter.default.publisher(for: .deviceDidShakeNotification)) { _ in
				loggedInUser = nil
			}
	}
	
	private var homeTabProvider: TabViewProvider {
		TabViewProvider(image: Image(NEWMAppViewModel.Tab.home), tabName: NEWMAppViewModel.Tab.home.description) {
			HomeView().erased
		}
	}
	
	private var walletTabProvider: TabViewProvider {
		TabViewProvider(image: Image(NEWMAppViewModel.Tab.wallet), tabName: NEWMAppViewModel.Tab.wallet.description) {
			WalletView().erased
		}
	}
}
