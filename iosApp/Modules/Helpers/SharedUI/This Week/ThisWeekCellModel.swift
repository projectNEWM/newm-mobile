import Foundation

public struct ThisWeekCellModel {
	public let iconImage: ImageAsset
	public let amountText: String
	public let labelText: String
	
	public init(iconImage: ImageAsset, amountText: String, labelText: String) {
		self.iconImage = iconImage
		self.amountText = amountText
		self.labelText = labelText
	}
}

extension ThisWeekCellModel: Identifiable {
	public var id: String { labelText }
}
