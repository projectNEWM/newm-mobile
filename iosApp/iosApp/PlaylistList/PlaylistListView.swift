//
//  PlaylistListView.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/11/22.
//

import SwiftUI

struct PlaylistListView: DataView {
	@ObservedObject var viewModel: PlaylistListViewModel
	
	init(id: String) {
		viewModel = PlaylistListViewModel(id: id)
	}
	
	var body: some View {
		ScrollView(.vertical, showsIndicators: false) {
			VStack {
				ForEach(viewModel.playlists) { playlist in
					PlaylistListViewCell(playlist: playlist)
						.padding(5)
				}
			}
		}
		.frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
		.background(Color.black)
	}
}

struct PlaylistView_Previews: PreviewProvider {
	static var previews: some View {
		PlaylistListView(id: "1")
	}
}
