import SwiftUI
import Resolver
import ModuleLinker

struct SongCell: View {
	var data: HomeViewModel.Song
	@Injected private var gradientTagProvider: GradientTagProviding
	@Injected private var circleImageProvider: CircleImageProviding
	@Injected private var fontProvider: FontProviding
	@Injected private var colorProvider: ColorProviding
		
	var body: some View {
		VStack {
			ZStack(alignment: .top) {
				circleImageProvider.circleImage(data.image, size: 60)
				if data.isNFT {
					gradientTagProvider.gradientTag(title: "NFT")
						.padding(.top, -14)
				}
			}
			Text(data.title)
				.foregroundColor(.white)
				.font(.caption3)
			Text(data.artist)
				.foregroundColor(colorProvider.color(for: .newmOrange))
				.font(.caption4)
		}
		.fixedSize()
		.frame(width: 105, height: 125)
	}
}

extension SongCell: HomeScrollingCell {
//	init(data: HomeViewModel.Song) {
//		self.data = data
//	}
	
	typealias DataType = HomeViewModel.Song
}

struct SongCell_Previews: PreviewProvider {
	static var previews: some View {
		Group {
			Resolver.resolve(SongCell.self, args: (title: "Song", isNFT: false))
			Resolver.resolve(SongCell.self, args: (title: "Song", isNFT: true))
		}
		.preferredColorScheme(.dark)
	}
}
