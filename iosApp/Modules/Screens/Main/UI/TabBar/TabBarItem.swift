import SwiftUI

struct TabBarItem: View {
	let image: Image
	let tab: MainViewModelTab
	
	var body: some View {
		VStack {
			image
			Text(tab.description)
		}
	}
}

extension View {
	func tabBarItem(
		image: Image,
		tab: MainViewModelTab
	) -> some View {
		tabItem {
			TabBarItem(
				image: image,
				tab: tab
			)
		}
	}
}

struct TabBarItem_Previews: PreviewProvider {
	static var previews: some View {
		TabBarItem(image: Image("pc"), tab: .library)
	}
}
