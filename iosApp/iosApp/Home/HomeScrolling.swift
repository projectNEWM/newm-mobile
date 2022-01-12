//
//  HomeScrolling.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/10/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

protocol HomeScrollingCellModel: View where DataType: Identifiable {
	associatedtype DataType
	
	init(_ data: DataType)
}

struct HomeScrollingContentView<Model: HomeScrollingCellModel>: View {
	@Binding var selectedDataModel: Model.DataType?
	let dataModels: [Model.DataType]
	let title: String
	
	var body: some View {
		VStack {
			SectionHeader(title: title)
			ScrollView(.horizontal, showsIndicators: false) {
				HStack(alignment: .firstTextBaseline, spacing: nil) {
					ForEach(dataModels) { dataModel in
						Model(dataModel)
							.onTapGesture { selectedDataModel = dataModel }
					}
				}
			}
		}
	}
}
