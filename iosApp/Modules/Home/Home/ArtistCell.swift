import SwiftUI
import SharedUI
import Colors

public struct ArtistCell: View {
	let data: HomeViewModel.Artist
	
	public var body: some View {
		VStack {
			artistImage
			artistName
			genre
			stars
		}
		.padding()
		.frame(width: 108, height: 158, alignment: .center)
		.background(backgroundGradient)
		.cornerRadius(10)
	}
	
	private var stars: some View {
		Text(data.stars)
			.font(.roboto(ofSize: 10))
			.opacity(0.97)
	}
	
	private var genre: some View {
		Text(data.genre)
			.foregroundColor(.newmPurple.opacity(0.97))
			.minimumScaleFactor(0.5)
			.font(.roboto(ofSize: 10))
			.padding(.bottom, 1)
			.padding(.top, -8)
	}
	
	private var artistImage: some View {
		CircleImage(UIImage(data: data.image) ?? UIImage.empty, size: 70)
			.padding(.bottom, 10)
	}
	
	private var artistName: some View {
		Text(data.name)
			.foregroundColor(.white)
			.lineLimit(2)
			.minimumScaleFactor(0.5)
			.multilineTextAlignment(.center)
			.font(.robotoMedium(ofSize: 11))
			.padding(.bottom, 1)
			.truncationMode(.middle)
	}
	
	private var backgroundGradient: some View {
		LinearGradient(colors: [.newmPurple, .newmOffPink],
								   startPoint: .top,
								   endPoint: .bottom)
			.opacity(0.35)
	}
}

extension ArtistCell: HomeScrollingCell {
	typealias DataType = HomeViewModel.Artist
}

struct ArtistCell_Previews: PreviewProvider {
	static var previews: some View {
		Group {
			ArtistCell(data: DummyData.makeArtist(name: "David Bowie"))
			ArtistCell(data: DummyData.makeArtist(name: "David Bowieasldkfjasldfkj"))
		}
		.preferredColorScheme(.dark)
	}
}
