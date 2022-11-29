import SwiftUI

public struct TabBar: View {
	let tabProviders: [TabViewProvider]
	let bottomPadding: CGFloat
	
	public init(tabProviders: [TabViewProvider], bottomPadding: CGFloat) {
		self.tabProviders = tabProviders
		self.bottomPadding = bottomPadding
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
				tabName: "First",
				viewProvider: { return AnyView(Text("First Tab")) }
			),
			.init(
				image: Image("heart.fill"),
				tabName: "Second",
				viewProvider: { return AnyView(Text("Second Tab")) }
			),
		], bottomPadding: 50).preferredColorScheme(.dark)
	}
}
