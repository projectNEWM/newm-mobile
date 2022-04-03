import SwiftUI
import Colors

struct ContentView: View {
	var body: some View {
		VStack(alignment: .trailing) {
			ForEach(NEWMColor.allCases, id: \.rawValue) { color in
				HStack {
					Text(color.rawValue)
					Circle()
						.foregroundColor(Color(color))
						.frame(width: 40, height: 40, alignment: .center)
				}
			}
		}
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
