import SwiftUI
import SharedUI

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
		HorizontalScroller(title: .thisWeek) {
			HStack(spacing: 12) {
				ForEach(cellModels) { model in
					ThisWeekCell(model)
				}
			}
		}
	}
}

struct ThisWeekSection_Previews: PreviewProvider {
	static var previews: some View {
		ThisWeekSection(MockHomeViewUIModelProvider.mockUIModel(actionHandler: MockHomeActionHandler()).thisWeekSection)
			.preferredColorScheme(.dark)
	}
}
