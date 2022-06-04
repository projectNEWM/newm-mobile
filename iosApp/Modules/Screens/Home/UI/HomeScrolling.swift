import SwiftUI
import ModuleLinker
import Resolver
import Fonts
import Colors
import SharedUI

protocol HomeScrollingCell: View where DataType: Identifiable {
	associatedtype DataType
	
	init(model: DataType)
}

struct HomeScrollingContentView<Model: HomeScrollingCell>: View {
	let selectedDataModel: (Model.DataType) -> ()
	let dataModels: [Model.DataType]
	let title: String
	let spacing: CGFloat
	
	var body: some View {
		VStack {
			sectionHeader(title: title)
			ScrollView(.horizontal, showsIndicators: false) {
				HStack(alignment: .center, spacing: nil) {
					ForEach(Array(dataModels.enumerated()), id: \.offset) { (offset, data) in
						Button(action: { selectedDataModel(data) }) {
							Model(model: data)
								.padding(.trailing, spacing)
						}
					}
				}
				.padding(.leading)
			}
			.tint(.white)
		}
	}
	
	private func sectionHeader(title: String) -> some View {
		Text(title)
			.frame(maxWidth: .infinity, alignment: .leading)
			.foregroundColor(Color(.grey100))
			.padding(.top)
			.padding(.leading)
			.font(.inter(ofSize: 12))
	}
}

extension BigArtistCell: HomeScrollingCell {
	typealias DataType = BigArtistViewModel
}

extension CompactArtistCell: HomeScrollingCell {
	typealias DataType = CompactArtistViewModel
}

struct HomeScrollingContentView_Previews: PreviewProvider {
	static var previews: some View {
		VStack {
			HomeScrollingContentView<BigArtistCell>(selectedDataModel: {_ in}, dataModels: MockData.artists.map(BigArtistViewModel.init), title: "NEWM Artists", spacing: 8)
			HomeScrollingContentView<CompactArtistCell>(selectedDataModel: {_ in}, dataModels: MockData.artists.map(CompactArtistViewModel.init), title: "Curated Playlists", spacing: 12)
		}
		.preferredColorScheme(.dark)
	}
}
