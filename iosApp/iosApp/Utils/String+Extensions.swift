//
//  String+Extensions.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/11/22.
//

import Foundation

extension String {
	var objectIdentifier: ObjectIdentifier { ObjectIdentifier(NSString(string: self)) }
}
