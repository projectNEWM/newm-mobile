import Foundation

public struct CellsSectionModel<CellModel> {
	public let cells: [CellModel]
	public let title: String
	
	public init(cells: [CellModel], title: String) {
		self.cells = cells
		self.title = title
	}
}
