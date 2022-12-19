import SwiftUI
import SharedUI
import Fonts
import shared
import Colors
import Resolver

struct PortfolioSection: View {
	let model: PortfolioSectionModel
	
	var body: some View {
		LazyVStack(alignment: .trailing) {
			pickerButton
			header
			songCells
		}
	}
}

extension PortfolioSection {
	@ViewBuilder
	private var pickerButton: some View {
		PickerButton(label: model.pickerLabel)
			.padding(.bottom)
			.foregroundColor(NEWMColor.grey100.swiftUIColor)
	}
	
	@ViewBuilder
	private var header: some View {
		HStack {
			Text(model.songHeaderTitle).font(.inter(ofSize: 14).bold())
			Spacer()
			Text(model.royaltyTitle).font(.inter(ofSize: 14).bold())
		}
	}
	
	@ViewBuilder
	private var songCells: some View {
		LazyVStack {
			ForEach(model.cells.indices) { index in
				cell(index: index, model.cells[index])
			}
		}
	}
	
	@ViewBuilder
	private func cell(index: Int, _ model: PortfolioSectionModel.Cell) -> some View {
		let size: CGFloat = 35
		HStack {
			AsyncImage(url: model.image) { image in
				image.circle(size: size)
			} placeholder: {
				Image.placeholder.circle(size: size)
			}
			Text(model.title)
			Spacer()
			Text(model.price)
		}
		.font(.inter(ofSize: 12).weight(.medium))
		.padding(8)
		.background(index % 2 == 0 ? NEWMColor.grey600.swiftUIColor : .black)
	}
}

struct PortfolioSection_Previews: PreviewProvider {
	static var previews: some View {
		PortfolioSection(model: Resolver.resolve(WalletViewModel.self).portfolioSection)
			.preferredColorScheme(.dark)
	}
}
