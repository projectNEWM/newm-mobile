import SwiftUI
import TabBar
import Home
import Login

public struct iOSApp: App {
	@State var viewModel = iOSAppViewModel()
	
	public init() { }
	
	public var body: some Scene {
		WindowGroup {
			TabBar(tabProviders: [
				homeTabProvider
			])
				.preferredColorScheme(.dark)
				.fullScreenCover(isPresented: .constant(viewModel.loggedInUser == nil)) { } content: {
					LoginView()
				}
//		TabView(selection: $viewModel.selectedTab) {
//			homeTabProvider
//			TribeView()
//				.tabItem {
//					Image(MainViewModel.Tab.tribe)
//					Text(MainViewModel.Tab.tribe)
//				}
//				.tag(MainViewModel.Tab.tribe)
//			StarsView()
//				.tabItem {
//					Image(MainViewModel.Tab.stars)
//					Text(MainViewModel.Tab.stars)
//				}
//				.tag(MainViewModel.Tab.stars)
//			WalletView()
//				.tabItem {
//					Image(MainViewModel.Tab.wallet)
//					Text(MainViewModel.Tab.wallet)
//				}
//				.tag(MainViewModel.Tab.wallet)
//			MoreTabView()
//				.tabItem {
//					Image(MainViewModel.Tab.more)
//					Text(MainViewModel.Tab.more)
//				}
//				.tag(MainViewModel.Tab.more)
//		}
		}
	}
	
	private var homeTabProvider: TabViewProvider {
		TabViewProvider(image: Image(iOSAppViewModel.Tab.home), tabName: iOSAppViewModel.Tab.home.description) {
			HomeView().erased
		}
	}
}
