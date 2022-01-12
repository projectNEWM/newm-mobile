//
//  ArtistCell.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/9/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct ArtistCell: View {
	struct Artist: Identifiable {
		let image: Data
		let name: String
		let genre: String
		let stars: Int
		let artistID: String
		var id: ObjectIdentifier { artistID.objectIdentifier }
	}

	let data: Artist
	
	var body: some View {
		VStack {
			CircleImage(image: UIImage(data: data.image) ?? UIImage.empty, size: 60)
			Text(data.name)
				.foregroundColor(.white)
				.minimumScaleFactor(0.5)
			Text(data.genre)
				.foregroundColor(.black)
				.minimumScaleFactor(0.5)
			Text("✭ \(data.stars)")
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

extension ArtistCell: HomeScrollingCellModel {
	typealias DataType = Artist
}

struct ArtistCell_Previews: PreviewProvider {
    static var previews: some View {
		Group {
			ArtistCell(data: DummyData.makeArtist(name: "David Bowie"))
			ArtistCell(data: DummyData.makeArtist(name: "David Bowieasldkfjasldfkj"))
		}
    }
}
