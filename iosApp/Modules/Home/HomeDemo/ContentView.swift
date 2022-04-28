import SwiftUI
import Home
import ModuleLinker
import Resolver

struct ContentView: View {
	@Injected var homeViewProvider: HomeViewProviding
    var body: some View {
		homeViewProvider.homeView()
			.preferredColorScheme(.dark)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
