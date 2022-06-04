import SwiftUI

struct MoreOfWhatYouLikeSection: View {
	private let cellTitleFont: Font = .inter(ofSize: 12).bold()
	private let cellSubtitleFont: Font = .inter(ofSize: 12)
	private let cellSubtitleColor: Color = Color(.grey100)
	
	let moreOfWhatYouLikes: [HomeViewModel.MoreOfWhatYouLike]
	let title: String

    var body: some View {
		LazyHStack(alignment: .top, spacing: 12) {
			ForEach(moreOfWhatYouLikes) { model in
				MoreOfWhatYouLikeCell(model: model)
			}
		}
		.addHorizontalScrollView(title: title)
    }
}

struct MoreOfWhatYouLikeSection_Previews: PreviewProvider {
    static var previews: some View {
		MoreOfWhatYouLikeSection(moreOfWhatYouLikes: MockData.moreOfWhatYouLikeCells, title: "MORE OF WHAT YOU LIKE")
			.preferredColorScheme(.dark)
    }
}
