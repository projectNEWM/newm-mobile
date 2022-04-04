import SwiftUI
import SongPlaying

struct ContentView: View {
    var body: some View {
		SongPlayingView(id: "1")
			.preferredColorScheme(.dark)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
