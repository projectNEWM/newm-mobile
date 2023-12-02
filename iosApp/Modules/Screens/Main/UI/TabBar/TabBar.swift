import SwiftUI
import Colors

struct TabBar: View {
	let tabProviders: [TabViewProvider]
	let bottomPadding: CGFloat
	
	init(tabProviders: [TabViewProvider], bottomPadding: CGFloat) {
		self.tabProviders = tabProviders
		self.bottomPadding = bottomPadding
	}
	
	var body: some View {
		TabView {
			ForEach(tabProviders, id: \.tab) { tabProvider in
				tabProvider.viewProvider()
					.tabItem {
						VStack {
							tabProvider.image
							Text(tabProvider.tab.description)
						}
					}
					.tag(tabProvider.tab)
					.padding(.bottom, bottomPadding)
			}
		}
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		TabBar(tabProviders: [
			.init(
				image: Image("heart.circle.fill"),
				tab: .library,
				tint: .pink,
				viewProvider: { return AnyView(Text("First Tab")) }
			),
			.init(
				image: Image("heart.fill"),
				tab: .profile,
				tint: .pink,
				viewProvider: { return AnyView(Text("Second Tab")) }
			),
		], bottomPadding: 50).preferredColorScheme(.dark)
	}
}
