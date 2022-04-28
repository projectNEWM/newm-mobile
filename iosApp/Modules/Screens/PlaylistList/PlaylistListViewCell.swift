import SwiftUI
import ModuleLinker
import Resolver

struct PlaylistListViewCell: View {
	let playlist: PlaylistListViewModel.Playlist
	let imageSize: CGFloat = 80
	
	@Injected private var circleImageProvider: CircleImageProviding
	@Injected private var colorProvider: ColorProviding
	@Injected private var fontProvider: FontProviding
	
	var body: some View {
		HStack {
			circleImageProvider.circleImage(playlist.image, size: imageSize)
				.padding(.trailing)
			VStack {
				title
				creator
				genreStarsPlays
			}
		}
		.frame(alignment: .leading)
		.padding()
		.padding(.trailing)
		.background(LinearGradient(colors: [colorProvider.color(for: .newmOffPink), colorProvider.color(for: .newmYellow)], startPoint: .top, endPoint: .bottom).opacity(0.31))
		.cornerRadius(imageSize)
	}
	
	private var title: some View {
		HStack {
			Text(playlist.title)
				.foregroundColor(.white)
				.font(fontProvider.robotoMedium(ofSize: 14))
			Spacer()
		}
		.padding(.bottom, 1)
	}
	
	private var creator: some View {
		HStack {
			Text(playlist.creator)
				.foregroundColor(colorProvider.color(for: .newmPink))
				.font(fontProvider.roboto(ofSize: 11))
			Spacer()
		}
		.padding(.bottom)
	}
	
	private var genreStarsPlays: some View {
		HStack {
			Text(playlist.genre)
			Text(playlist.starCount)
			Text(playlist.playCount)
			Spacer()
		}
		.font(fontProvider.roboto(ofSize: 10))
		.foregroundColor(.white.opacity(0.97))
	}
}

struct PlaylistListViewCell_Previews: PreviewProvider {
	static var previews: some View {
		let playlist: PlaylistListViewModel.Playlist = Resolver.resolve(args: "1")
		PlaylistListViewCell(playlist: playlist)
			.preferredColorScheme(.dark)
	}
}
