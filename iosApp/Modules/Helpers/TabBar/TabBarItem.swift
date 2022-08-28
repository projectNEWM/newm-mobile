import SwiftUI

struct TabBarItem: View {
	let image: Image
	let tabName: String
	
	var body: some View {
		VStack {
			image
			Text(tabName)
		}
	}
}

extension View {
	func tabBarItem(
		image: Image,
		tabName: String
	) -> some View {
		tabItem {
			TabBarItem(
				image: image,
				tabName: tabName
			)
		}
	}
}

struct TabBarItem_Previews: PreviewProvider {
	static var previews: some View {
		TabBarItem(image: Image("pc"), tabName: "a tab")
	}
}
