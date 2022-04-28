import SwiftUI
import TabBar
import Resolver
import ModuleLinker

public struct MainView: View {
	@State var viewModel: MainViewModelProtocol = Resolver.resolve()
	@AppStorage("loggedInUser") var loggedInUser: String?
	@Injected private var tabProviders: [TabViewProvider]
	@Injected private var loginViewProvider: LoginViewProviding

	public init() {}
	
	public var body: some View {
		TabBar(tabProviders: tabProviders)
			.preferredColorScheme(.dark)
			.fullScreenCover(isPresented: .constant(loggedInUser == nil)) { } content: {
				loginViewProvider.loginView()
			}
		//TODO: remove this
			.onReceive(NotificationCenter.default.publisher(for: .deviceDidShakeNotification)) { _ in
				loggedInUser = nil
			}
	}
}
