import SwiftUI
import TabBar
import Resolver
import ModuleLinker

public struct MainView: View {
	@StateObject var viewModel = MainViewModel()
	@Injected private var tabProviders: [TabViewProvider]
	@Injected private var loginViewProvider: LoginViewProviding

	public init() {}
	
	public var body: some View {
		TabBar(tabProviders: tabProviders)
			.preferredColorScheme(.dark)
			.fullScreenCover(isPresented: $viewModel.shouldShowLogin) { } content: {
				loginViewProvider.loginView()
			}
	}
}

struct MainView_Previews: PreviewProvider {
	static var previews: some View {
		MainView()
	}
}
