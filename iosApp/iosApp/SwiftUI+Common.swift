//
//  SwiftUI+Common.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/10/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct SectionHeader: View {
	let title: String
	
	var body: some View {
		Text(title)
			.frame(maxWidth: .infinity, alignment: .leading)
			.foregroundColor(.white)
			.padding(.top)
	}
}

extension Image {
	static func roundImage(_ image: UIImage, size: CGFloat) -> some View {
		Image(uiImage: image)
			.resizable()
			.frame(width: size, height: size, alignment: .center)
			.cornerRadius(size / 2.0)
	}
}
