import SwiftUI
import Fonts
import Colors

public struct ThisWeekCell: View {
	private let iconSize: CGFloat = 32
	
	private let model: ThisWeekCellModel
	
	init(_ model: ThisWeekCellModel) {
		self.model = model
	}
	
	public var body: some View {
		VStack(alignment: .leading) {
			icon
			amount
			label
		}
		.padding()
		.background(NEWMColor.grey600())
		.cornerRadius(8)
	}
}

extension ThisWeekCell {
	private var icon: some View {
		model.iconImage.swiftUIImage
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
			.foregroundColor(NEWMColor.grey100())
	}
}

struct ThisWeekCell_Previews: PreviewProvider {
	static var previews: some View {
		ThisWeekCell(ThisWeekCellModel(iconImage: Asset.Media.heartIcon, amountText: "+56", labelText: "Followers"))
			.preferredColorScheme(.dark)
	}
}
