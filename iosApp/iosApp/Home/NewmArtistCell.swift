//
//  NewmArtistCell.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/9/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct NewmArtistCell: View {
	let artist: NewmArtist
	
	var body: some View {
		VStack {
			Image.roundImage(UIImage(data: artist.image) ?? UIImage.placeholder, size: 60)
			Text(artist.name)
				.foregroundColor(.white)
				.minimumScaleFactor(0.5)
			Text(artist.genre)
				.foregroundColor(.black)
				.minimumScaleFactor(0.5)
			Text("✭ \(artist.stars)")
		}
		.padding()
		.fixedSize()
		.frame(width: 130, height: 200, alignment: .center)
		.background(LinearGradient(colors: [.pink, .purple],
								   startPoint: .top,
								   endPoint: .bottom))
		.cornerRadius(10)
	}
}

extension NewmArtistCell: HomeScrollingCellModel {
	typealias DataType = NewmArtist
	
	init(_ data: NewmArtist) {
		self.artist = data
	}
}

struct NewmArtistCell_Previews: PreviewProvider {
    static var previews: some View {
		Group {
			NewmArtistCell(artist: DummyData.makeNewmArtist(name: "David Bowie"))
			NewmArtistCell(artist: DummyData.makeNewmArtist(name: "David Bowieasldkfjasldfkj"))
		}
    }
}
