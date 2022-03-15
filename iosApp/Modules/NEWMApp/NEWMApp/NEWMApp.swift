import SwiftUI
import TabBar
import Login

public struct NEWMApp: View {
	@State var viewModel = NEWMAppViewModel()
	@AppStorage("loggedInUser") var loggedInUser: String?
	let tabProvider = TabProviderFactory()
	
	public init() { }
	
	public var body: some View {
		TabBar(tabProviders: [
			tabProvider.makeTabProvider(for: .home),
			tabProvider.makeTabProvider(for: .wallet)
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
}
