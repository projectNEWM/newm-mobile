//
//  SongCell.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/9/22.
//

import SwiftUI

struct SongCell: View {
	var data: HomeViewModel.Song
	
	var body: some View {
		VStack {
			ZStack(alignment: .top) {
				CircleImage(image: data.image, size: 60)
				if data.isNFT {
					GradientTag(title: "NFT")
						.padding(.top, -14)
				}
			}
			Text(data.title)
				.foregroundColor(.white)
				.font(.caption3)
			Text(data.artist)
				.foregroundColor(.orange)
				.font(.caption4)
		}
		.fixedSize()
		.frame(width: 80, height: 150)
	}
}

extension SongCell: HomeScrollingCell {
	typealias DataType = HomeViewModel.Song
}

struct SongCell_Previews: PreviewProvider {
	static var previews: some View {
		Group {
			SongCell(data: DummyData.makeSong(title: "Song", isNFT: false))
							.background(Color.black)
			SongCell(data: DummyData.makeSong(title: "Song", isNFT: true))
							.background(Color.black)
		}
	}
}
