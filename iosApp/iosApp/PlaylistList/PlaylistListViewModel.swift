//
//  PlaylistListViewModel.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/16/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import UIKit.UIImage

class PlaylistListViewModel: ObservableObject {
	let id: String
	@Published var playlist: [Playlist] = DummyData.playlistListPlaylists

	init(id: String) {
		self.id = id
	}
}

extension PlaylistListViewModel {
	struct Playlist: Identifiable {
		let image: UIImage
		let title: String
		let creator: String
		let genre: String
		let starCount: String
		let playCount: String
		let playlistID: String
		var id: ObjectIdentifier { playlistID.objectIdentifier }
	}
}
