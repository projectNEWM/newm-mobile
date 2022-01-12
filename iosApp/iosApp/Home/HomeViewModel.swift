//
//  HomeViewModel.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/10/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

class HomeViewModel: ObservableObject {
	enum Section: CaseIterable {
		case explore
		case ambient
		case hipHop
		case alternative
	}
	
	@Published var artists: [ArtistCell.Artist] = DummyData.artists
	@Published var selectedArtist: ArtistCell.Artist? = nil
	
	@Published var songs: [SongCell.Song] = DummyData.songs
	@Published var selectedSong: SongCell.Song? = nil
	
	@Published var playlists: [PlaylistCell.Playlist] = DummyData.playlists
	@Published var selectedPlaylist: PlaylistCell.Playlist? = nil

	@Published var selectedSectionIndex: Int = 0
	let sections = Section.allCases.map(\.description)
}

extension HomeViewModel {
	func deselectAll() {
		selectedArtist = nil
		selectedSong = nil
		selectedPlaylist = nil
	}
}

extension HomeViewModel.Section: CustomStringConvertible {
	var description: String {
		switch self {
		case .alternative: return "Alternative"
		case .ambient: return "Ambient"
		case .explore: return "Explore"
		case .hipHop: return "Hip Hop"
		}
	}
}
