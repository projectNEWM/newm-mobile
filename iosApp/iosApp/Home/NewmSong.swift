//
//  NewmSong.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/9/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

struct NewmSong {
	let image: Data
	let title: String
	let artist: String
}

extension NewmSong: Identifiable {
	var id: ObjectIdentifier { ObjectIdentifier(NSString(string: title)) }
}
