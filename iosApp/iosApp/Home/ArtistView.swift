//
//  ArtistView.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/10/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct ArtistView: View {
	let artist: NewmArtist
//	@EnvironmentObject var homeViewModel: HomeViewModel

    var body: some View {
		Text(artist.name)
//			.onDisappear { homeViewModel.selectedArtist = nil }
    }
}

//struct ArtistView_Previews: PreviewProvider {
//    static var previews: some View {
//		ArtistView()
//    }
//}
