import SwiftUI

struct PlaylistDetailsView: DataView {
	init(id: String) {
		
	}
	
    var body: some View {
        Text("Hello, World!")
    }
}

struct PlaylistDetailsView_Previews: PreviewProvider {
    static var previews: some View {
		PlaylistDetailsView(id: "1")
    }
}
