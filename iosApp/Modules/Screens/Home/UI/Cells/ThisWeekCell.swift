import SwiftUI
import Fonts
import Colors

struct ThisWeekCell: View {
	private let iconSize: CGFloat = 32
	
	private let model: ThisWeekCellModel
	
	init(_ model: ThisWeekCellModel) {
		self.model = model
	}
	
	var body: some View {
		VStack(alignment: .leading) {
			icon
			amount
			label
		}
		.padding()
		.background(Color(.grey600))
		.cornerRadius(8)
	}
	
	private var icon: some View {
		model.iconImage.image
			.frame(width: iconSize, height: iconSize)
			.cornerRadius(4)
	}
	
	private var amount: some View {
		Text(model.amountText)
			.font(.thisWeekCellAmountFont)
			.padding(.bottom, 0)
	}
		
	private var label: some View {
		Text(model.labelText)
			.font(.inter(ofSize: 12))
			.foregroundColor(Color(.grey100))
	}
}

struct ThisWeekCell_Previews: PreviewProvider {
	static var previews: some View {
		ThisWeekCell(ThisWeekCellModel(iconImage: .heart, amountText: "+56", labelText: "Followers this week"))
			.preferredColorScheme(.dark)
	}
}
