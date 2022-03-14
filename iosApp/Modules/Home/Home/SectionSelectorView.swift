import SwiftUI

public struct SectionSelectorView: View {
	@Binding var selectedIndex: Int
	let sectionTitles: [String]
	
	public var body: some View {
		ScrollView(.horizontal, showsIndicators: false) {
			HStack {
				ForEach(0..<sectionTitles.count) { index in
					Button(sectionTitles[index]) {
						selectedIndex = index
					}
					.foregroundColor(textColor(for: index))
					.font(font(for: index))
				}
			}
			.frame(maxWidth: .infinity, alignment: .leading)
			.padding([.top, .bottom])
			.padding(.leading, 16)
		}
		.background(Color.black)
	}
	
	private func textColor(for index: Int) -> Color {
		selectedIndex == index ? .white : .gray
	}
	
	private func font(for index: Int) -> Font {
		selectedIndex == index ? .newmFontBold(ofSize: 16) : .newmFont(ofSize: 16)
	}
}

struct SectionSelectorView_Previews: PreviewProvider {
	@State static var selectedIndex: Int = 0
	static var previews: some View {
		SectionSelectorView(selectedIndex: $selectedIndex,
							sectionTitles: HomeViewModel.Section.allCases.map(\.description))
	}
}
