//
//  String+Extensions.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/11/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

extension String {
	var objectIdentifier: ObjectIdentifier { ObjectIdentifier(NSString(string: self)) }
}
