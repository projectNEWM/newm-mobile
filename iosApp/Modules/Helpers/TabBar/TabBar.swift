import SwiftUI

public struct TabBar: View {
	let tabProviders: [TabViewProvider]
	
	public init(tabProviders: [TabViewProvider]) {
		self.tabProviders = tabProviders
	}
	
	public var body: some View {
		TabView {
			ForEach(tabProviders, id: \.tabName) { tabProvider in
				tabProvider.viewProvider()
					.tabBarItem(
						image: tabProvider.image,
						tabName: ""
					)
					.tag(tabProvider.tabName)
			}
		}
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		TabBar(tabProviders: [
			.init(
				image: Image("heart.circle.fill"),
				tabName: "First",
				viewProvider: { return AnyView(Text("First Tab")) }
			),
			.init(
				image: Image("heart.fill"),
				tabName: "Second",
				viewProvider: { return AnyView(Text("Second Tab")) }
			),
		])
	}
}
