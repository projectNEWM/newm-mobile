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
	
	@Published var newmArtists: [NewmArtist] = DummyData.newmArtists
	@Published var selectedArtist: NewmArtist? = nil
	
	@Published var newmSongs: [NewmSong] = DummyData.newmSongs
	@Published var selectedSong: NewmSong? = nil
	
	@Published var selectedSectionIndex: Int = 0
	let sections = Section.allCases.map(\.description)
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
