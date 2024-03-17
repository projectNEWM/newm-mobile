//import SwiftUI
//import shared
//import AudioPlayer
//
//struct FilterSortMenuView: View {
//	@Binding var filterOptions: Filters
//	@Binding var selectedSortOption: AudioPlayerSort
//
//	var body: some View {
//		NavigationView {
//			Form(content: {
//				Section(header: Text("Search")) {
//					TextField("Search by title or artist", value: $filterOptions.text, formatter: Formatter())
//				}
//				
//				Section(header: Text("Filter Length")) {
//					TextField("Maximum Length (seconds)", value: $filterOptions.duration, formatter: NumberFormatter())
//						.keyboardType(.numberPad)
//				}
//				
//				Section(header: Text("Sort By")) {
//					Picker("Sort Option", selection: $selectedSortOption) {
//						ForEach(AudioPlayerSort.allCases, id: \.self) { option in
//							Text(option.rawValue).tag(option)
//						}
//					}.pickerStyle(SegmentedPickerStyle())
//				}
//			})
//			.navigationBarTitle("Filter & Sort", displayMode: .inline)
//		}
//	}
//}
////
////extension SortOption: CaseIterable {
////	public static var allCases: [SortOption] {
////		entries
////	}
////}
