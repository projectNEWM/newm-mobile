import SwiftUI

struct ThisWeekSection: View {
	private let cellModels: [ThisWeekCellModel]
	
	init(model: HomeViewUIModel.ThisWeekSectionModel) {
		cellModels = [
			ThisWeekCellModel(iconImage: .heart, amountText: "+\(model.newFollowers)", labelText: .followersThisWeek),
			ThisWeekCellModel(iconImage: .royalties, amountText: "$\(model.royalties)", labelText: .royaltiesThisWeek),
			ThisWeekCellModel(iconImage: .earnings, amountText: "+\(model.earnings)", labelText: .earnings)
		]
	}
	
	var body: some View {
		HStack {
			ForEach(cellModels) { model in
				ThisWeekCell(model)
			}
		}
		.addHorizontalScrollView(title: .thisWeek)
	}
}

struct ThisWeekSection_Previews: PreviewProvider {
	static var previews: some View {
		ThisWeekSection(model: HomeViewUIModel.ThisWeekSectionModel(newFollowers: 12, royalties: 32.12, earnings: 19.23))
			.preferredColorScheme(.dark)
	}
}
