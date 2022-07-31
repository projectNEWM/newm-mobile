import SwiftUI

struct ThisWeekSection: View {
	private let cellModels: [ThisWeekCellModel]
	
	init(_ model: ThisWeekSectionModel) {
		cellModels = [
			ThisWeekCellModel(iconImage: .heart, amountText: "+\(model.newFollowers)", labelText: .followersThisWeek),
			ThisWeekCellModel(iconImage: .royalties, amountText: "$\(model.royalties)", labelText: .royaltiesThisWeek),
			ThisWeekCellModel(iconImage: .earnings, amountText: "+\(model.earnings)", labelText: .earningsThisWeek)
		]
	}
	
	var body: some View {
		HStack(spacing: 12) {
			ForEach(cellModels) { model in
				ThisWeekCell(model)
			}
		}
		.addHorizontalScrollView(title: .thisWeek)
	}
}

struct ThisWeekSection_Previews: PreviewProvider {
	static var previews: some View {
		ThisWeekSection(MockHomeViewUIModelProvider.mockUIModel.thisWeekSection)
			.preferredColorScheme(.dark)
	}
}
