//
//  HomeViewNavigationLinks.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/11/22.
//

import Foundation
import SwiftUI

struct IDLink<LinkedView: DataView>: View {
	let selectedID: String?
	
	var body: some View {
		NavigationLink(isActive: .constant(selectedID != nil), destination: {
			if let selectedID = selectedID {
				LinkedView(id: selectedID)
			} else {
				EmptyView()
			}
		}, label: { EmptyView() })
	}
}
