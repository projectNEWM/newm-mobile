import SwiftUI
import ModuleLinker
import Resolver

public struct ArtistCell: View {
	let data: HomeViewModel.Artist
	@Injected private var fonts: FontProviding
	@Injected private var colors: ColorProviding
	
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
			.font(fonts.roboto(ofSize: 10))
			.opacity(0.97)
	}
	
	private var genre: some View {
		Text(data.genre)
			.foregroundColor(colors.color(for: .newmPurple).opacity(0.97))
			.minimumScaleFactor(0.5)
			.font(fonts.roboto(ofSize: 10))
			.padding(.bottom, 1)
			.padding(.top, -8)
	}
	
	private var artistImage: some View {
		AsyncImage(url: data.image) { image in
			if case let .success(image) = image {
				image
					.circleImage(size: 70)
					.padding(.bottom, 10)
			}
		}
	}
	
	private var artistName: some View {
		Text(data.name)
			.foregroundColor(.white)
			.lineLimit(2)
			.minimumScaleFactor(0.5)
			.multilineTextAlignment(.center)
			.font(fonts.robotoMedium(ofSize: 11))
			.padding(.bottom, 1)
			.truncationMode(.middle)
	}
	
	private var backgroundGradient: some View {
		LinearGradient(colors: [colors.color(for: .newmPurple), colors.color(for: .newmOffPink)],
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
			ArtistCell(data: Resolver.resolve(HomeViewModel.Artist.self, args: "David Bowie"))
			ArtistCell(data: Resolver.resolve(HomeViewModel.Artist.self, args: "David Bowieasldkfjasldfkj"))
		}
		.preferredColorScheme(.dark)
	}
}
