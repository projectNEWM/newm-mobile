//
//  HomeViewModel.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/10/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import Combine
//TODO: Remove UIImage uses and replace with URLs
import UIKit.UIImage

class HomeViewModel: ObservableObject {
	enum Section: CaseIterable {
		case explore
		case ambient
		case hipHop
		case alternative
	}
	
	@Published var title: String = "NEWM"
	
	@Published var artistSectionTitle: String = "NEWM Artists"
	@Published var artists: [HomeViewModel.Artist] = DummyData.artists
	@Published var selectedArtist: HomeViewModel.Artist? = nil
	
	@Published var songsSectionTitle: String = "NEWM Songs"
	@Published var songs: [HomeViewModel.Song] = DummyData.songs
	@Published var selectedSong: HomeViewModel.Song? = nil
	
	@Published var playlistsSectionTitle: String = "Curated Playlists"
	@Published var playlists: [HomeViewModel.Playlist] = DummyData.playlists
	@Published var selectedPlaylist: HomeViewModel.Playlist? = nil

	@Published var selectedSectionIndex: Int = 0
	let sections = Section.allCases.map(\.description)
}

extension HomeViewModel {
	struct Artist: Identifiable {
		let image: Data
		let name: String
		let genre: String
		let stars: String
		let artistID: String
		var id: ObjectIdentifier { artistID.objectIdentifier }
	}

	struct Song: Identifiable {
		let image: UIImage
		let title: String
		let artist: String
		let isNFT: Bool
		let songID: String
		var id: ObjectIdentifier { title.objectIdentifier }
	}
	
	struct Playlist: Identifiable {
		let image: UIImage
		let title: String
		let creator: String
		let songCount: String
		let playlistID: String
		var id: ObjectIdentifier { playlistID.objectIdentifier }
	}
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
