import SwiftUI
import ModuleLinker
import Resolver
import SharedUI

struct PlaylistCell: View {
	let data: HomeViewModel.Playlist
	
	@Injected private var fontProvider: FontProviding
	@Injected private var colorProvider: ColorProviding

    var body: some View {
		VStack {
			playlistImage
			title
			creator
			songCount
		}
		.padding(10)
		.frame(width: 107, height: 191, alignment: .top)
		.background(backgroundGradient)
		.cornerRadius(60)
    }
	
	private var playlistImage: some View {
		AsyncImage(url: data.image) { image in
			image.circleImage(size: 70)
				.padding(.top, 5)
		} placeholder: {
			//TODO: add placeholder
		}
	}
	
	private var title: some View {
		Text(data.title)
			.multilineTextAlignment(.center)
			.foregroundColor(.white)
			.font(fontProvider.robotoMedium(ofSize: 14))
			.padding(.bottom, 2)
			.lineLimit(2)
			.minimumScaleFactor(0.5)
	}
	
	private var creator: some View {
		Text(data.creator)
			.foregroundColor(colorProvider.color(for: .newmPink).opacity(0.97))
			.font(fontProvider.roboto(ofSize: 11))
			.padding(.bottom, 2)
	}
	
	private var songCount: some View {
		Text(data.songCount)
			.foregroundColor(.white)
			.font(fontProvider.roboto(ofSize: 11))
			.padding(.bottom, 4)
	}
	
	private var backgroundGradient: some View {
		LinearGradient(colors: [colorProvider.color(for: .newmOffPink), colorProvider.color(for: .newmYellow)], startPoint: .top, endPoint: .bottom).opacity(0.26)
	}
}

extension PlaylistCell: HomeScrollingCell {
	typealias DataType = HomeViewModel.Playlist
}

struct PlaylistCell_Previews: PreviewProvider {
    static var previews: some View {
		Resolver.resolve(PlaylistCell.self, args: "1")
			.preferredColorScheme(.dark)
    }
}
