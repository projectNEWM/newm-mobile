//
//  NewmArtist.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/9/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

struct NewmArtist {
	let image: Data
	let name: String
	let genre: String
	let stars: Int
}

extension NewmArtist: Identifiable {
	var id: ObjectIdentifier { ObjectIdentifier(NSString(string: name)) }
}
