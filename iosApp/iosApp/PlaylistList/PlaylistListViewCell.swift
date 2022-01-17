//
//  PlaylistListViewCell.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/16/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct PlaylistListViewCell: View {
	let playlist: PlaylistListViewModel.Playlist
	
    var body: some View {
		HStack {
			Image(uiImage: playlist.image)
			VStack {
				Text(playlist.title)
				Text(playlist.creator)
				HStack {
					Text(playlist.genre)
					Text(playlist.starCount)
					Text(playlist.playCount)
				}
			}
		}
    }
}

struct PlaylistViewCell_Previews: PreviewProvider {
    static var previews: some View {
		PlaylistListViewCell(playlist: DummyData.makePlaylistListPlaylist(id: "1"))
    }
}
