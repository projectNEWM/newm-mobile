//import SwiftUI
//import Resolver
//import ModuleLinker
//import Fonts
//import SharedUI
//
//struct SongCell: View {
//	var data: HomeViewModel.Song
//	@Injected private var gradientTagProvider: GradientTagProviding
//	@Injected private var colorProvider: ColorProviding
//	
//	var body: some View {
//		VStack {
//			ZStack(alignment: .top) {
//				AsyncImage(url: data.image) { phase in
//					if case let .success(image) = phase {
//						image
//							.circleImage(size: 60)
//							.padding(.top, -14)
//					}
//				}
//				if data.isNFT {
//					gradientTagProvider.gradientTag(title: "NFT")
//						.padding(.top, -14)
//				}
//			}
//			Text(data.title)
//				.foregroundColor(.white)
//				.font(.caption3)
//			Text(data.artist)
//				.foregroundColor(colorProvider.color(for: .newmOrange))
//				.font(.caption4)
//		}
//		.fixedSize()
//		.frame(width: 105, height: 125)
//	}
//}
//
//extension SongCell: HomeScrollingCell {
//	typealias DataType = HomeViewModel.Song
//}
//
//struct SongCell_Previews: PreviewProvider {
//	static var previews: some View {
//		ScrollView(.horizontal) {
//			HStack {
//				ForEach(MockData.songs.map(HomeViewModel.Song.init), id: \.id) {
//					SongCell(data: $0)
//				}
//			}
//		}
//		.preferredColorScheme(.dark)
//	}
//}
