import SwiftUI

public struct ThisWeekSectionModel {
	let title: String
	let cells: [ThisWeekCellModel]
	
	public init(title: String, cells: [ThisWeekCellModel]) {
		self.title = title
		self.cells = cells
	}
}

public struct ThisWeekSection: View {
	private let model: ThisWeekSectionModel
	
	public init(_ model: ThisWeekSectionModel) {
		self.model = model
	}
	
	public var body: some View {
		HorizontalScroller(title: model.title) {
			HStack(spacing: 12) {
				ForEach(model.cells, id: \.labelText, content: ThisWeekCell.init)
			}
		}
	}
}

struct ThisWeekSection_Previews: PreviewProvider {
	static var previews: some View {
		ThisWeekSection(ThisWeekSectionModel(title: "THIS WEEK", cells: [
			ThisWeekCellModel(iconImage: Asset.Media.royaltiesIcon, amountText: "$42.39", labelText: "Royalties"),
			ThisWeekCellModel(iconImage: Asset.Media.earningsIcon, amountText: "+24.34%", labelText: "Earnings"),
			ThisWeekCellModel(iconImage: Asset.Media.heartIcon, amountText: "+30", labelText: "Followers")
		]))
		.preferredColorScheme(.dark)
	}
}
