//
//  UIImage+Extensions.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/7/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import UIKit
import SwiftUI

extension UIImage {
	static var empty: UIImage {
		UIImage()
	}
}

struct CircleImage: View {
	let image: UIImage
	let size: CGFloat
	
	var body: some View {
		Image(uiImage: image)
			.resizable()
			.frame(width: size, height: size, alignment: .center)
			.cornerRadius(size / 2.0)
	}
}
