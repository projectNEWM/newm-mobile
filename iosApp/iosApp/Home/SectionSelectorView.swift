//
//  SectionSelectorView.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/9/22.
//

import SwiftUI

struct SectionSelectorView: View {
	@Binding var selectedIndex: Int
	let sectionTitles: [String]
	
	var body: some View {
		ScrollView(.horizontal, showsIndicators: false) {
			HStack {
				ForEach(0..<sectionTitles.count) { index in
					Button(sectionTitles[index]) {
						selectedIndex = index
					}
					.foregroundColor(selectedIndex == index ? .white : .gray)
					.font(Font.system(size: 16, weight: selectedIndex == index ? .heavy : .medium, design: .default))
				}
			}
			.frame(maxWidth: .infinity, alignment: .leading)
			.padding([.top, .bottom])
		}
		.background(Color.black)
	}
}

struct SectionSelectorView_Previews: PreviewProvider {
	static var previews: some View {
		SectionSelectorView(selectedIndex: .constant(0),
							sectionTitles: HomeViewModel.Section.allCases.map(\.description))
	}
}
