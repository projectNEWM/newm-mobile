import SwiftUI
import shared

struct FilterSortMenuView: View {
	@Binding var filterOptions: FilterOptions
	@Binding var selectedSortOption: SortOption

	var body: some View {
		NavigationView {
			Form(content: {
				Section(header: Text("Search")) {
					TextField("Search by title or artist", text: $filterOptions.searchText)
				}
				
				Section(header: Text("Filter Length")) {
					TextField("Maximum Length (seconds)", value: $filterOptions.maxLength, formatter: NumberFormatter())
						.keyboardType(.numberPad)
				}
				
				Section(header: Text("Sort By")) {
					Picker("Sort Option", selection: $selectedSortOption) {
//						ForEach(SortOption.allCases) { option in
//							Text(option.rawValue).tag(option)
//						}
					}.pickerStyle(SegmentedPickerStyle())
				}
			})
			.navigationBarTitle("Filter & Sort", displayMode: .inline)
		}
	}
}
//
//extension SortOption: CaseIterable {
//	public static var allCases: [SortOption] {
//		entries
//	}
//}
