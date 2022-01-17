//
//  PlaylistListView.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/11/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct PlaylistListView: DataView {
	@ObservedObject var viewModel: PlaylistListViewModel
	
	init(id: String) {
		viewModel = PlaylistListViewModel(id: id)
	}
	
    var body: some View {
        Text("hi")
    }
}

//struct PlaylistView_Previews: PreviewProvider {
//    static var previews: some View {
//        PlaylistView()
//    }
//}
