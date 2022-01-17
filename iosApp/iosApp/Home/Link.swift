//
//  HomeViewNavigationLinks.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/11/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct Link<LinkedView: DataView>: View {
	let selectedID: String?
	
	var body: some View {
		NavigationLink(isActive: .constant(selectedID != nil), destination: {
			if let selectedID = selectedID {
				LinkedView(ID: selectedID)
			} else {
				EmptyView()
			}
		}, label: { EmptyView() })
	}
}
