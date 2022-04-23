import SwiftUI
import TabBar
import Login
import Resolver
import ModuleLinker

public struct MainView: View {
	@State var viewModel: MainViewModelProtocol = Resolver.resolve()
	@AppStorage("loggedInUser") var loggedInUser: String?
	@Injected var tabProviders: [TabViewProvider]
	
	public init() {}
	
	public var body: some View {
		TabBar(tabProviders: tabProviders)
			.preferredColorScheme(.dark)
			.fullScreenCover(isPresented: .constant(loggedInUser == nil)) { } content: {
				LoginView()
			}
		//TODO: remove this
			.onReceive(NotificationCenter.default.publisher(for: .deviceDidShakeNotification)) { _ in
				loggedInUser = nil
			}
	}
}
