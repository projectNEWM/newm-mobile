import SwiftUI
import SharedUI
import Resolver
import Fonts
import Colors

struct TransactionsView: View {
	let model: TransactionsSectionModel
	
    var body: some View {
		VStack {
			ForEach(model.sections, id: \.title) {
				TransactionsSection(model: $0)
				NEWMColor.grey600.swiftUIColor
					.frame(height: 2)
			}
			.padding(.bottom, 20)
		}
    }
}

struct TransactionsSection: View {
	let model: TransactionsSectionModel.Section
	
	var body: some View {
		VStack(alignment: .leading, spacing: 20) {
			Text(model.title)
				.font(.inter(ofSize: 12).weight(.semibold))
			ForEach(0..<model.cells.count) {
				TransactionCell.init(model: model.cells[$0])
			}
		}
	}
}

struct TransactionCell: View {
	let model: TransactionsSectionModel.Cell
	
	var body: some View {
		HStack {
			Image(asset: model.image)
			VStack(alignment: .leading) {
				HStack {
					Text(model.title1)
					if let title2 = model.title2 {
						Text("\(title2)").foregroundColor(NEWMColor.grey100.swiftUIColor)
					}
				}
				Text(model.time).foregroundColor(NEWMColor.grey200.swiftUIColor)
			}.font(.inter(ofSize: 12).weight(.medium))
			Spacer()
			Text(model.amount).font(.inter(ofSize: 12).bold())
		}
	}
}

struct Transactions_Previews: PreviewProvider {
    static var previews: some View {
		TransactionsView(model: Resolver.resolve(WalletViewModel.self).transactionsSection)
			.preferredColorScheme(.dark)
    }
}
