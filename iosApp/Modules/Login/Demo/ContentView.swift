import SwiftUI
import Login

struct ContentView: View {
    var body: some View {
		LoginView()
	}
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
		ZStack {
			LoginView()
		}
    }
}
