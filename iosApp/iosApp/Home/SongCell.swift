//
//  SongCell.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/9/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct SongCell: View {
	struct Song: Identifiable {
		let image: UIImage
		let title: String
		let artist: String
		let isNFT: Bool
		let songID: String
		var id: ObjectIdentifier { title.objectIdentifier }
	}
	
	var data: Song
	
	var body: some View {
		VStack {
			ZStack(alignment: .top) {
				CircleImage(image: data.image, size: 60)
				if data.isNFT {
					nftLabel
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
	
	private var nftLabel: some View {
		Text("NFT")
			.font(.caption3.weight(.bold))
			.padding([.top, .bottom], 4)
			.padding([.leading, .trailing], 10)
			.background(LinearGradient(colors: [.green, .blue], startPoint: .top, endPoint: .bottom))
			.cornerRadius(20)
	}
}

extension SongCell: HomeScrollingCellModel {
	typealias DataType = Song
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
