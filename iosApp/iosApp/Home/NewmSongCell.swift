//
//  NewmSongCell.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/9/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct NewmSongCell: View {
	var song: NewmSong
	
	var body: some View {
		VStack {
			DummyData.roundArtistImage
			Text(song.title)
				.foregroundColor(.white)
				.font(.caption3)
			Text(song.artist)
				.foregroundColor(.orange)
				.font(.caption4)
		}
		.fixedSize()
		.frame(width: 80, height: 150)
	}
}

extension NewmSongCell: HomeScrollingCellModel {
	typealias DataType = NewmSong
	
	init(_ data: NewmSong) {
		self.song = data
	}
}

struct NewmSongCell_Previews: PreviewProvider {
	static var previews: some View {
		NewmSongCell(song: DummyData.makeNewmSong(title: "Song"))
			.background(Color.black)
	}
}
