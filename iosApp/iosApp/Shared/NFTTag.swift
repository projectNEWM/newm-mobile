//
//  NFTTag.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/16/22.
//

import SwiftUI

struct NFTTag: View {
    var body: some View {
		Text("NFT")
			.font(.caption3.weight(.bold))
			.padding([.top, .bottom], 4)
			.padding([.leading, .trailing], 10)
			.background(LinearGradient(colors: [.green, .blue], startPoint: .top, endPoint: .bottom))
			.cornerRadius(20)
    }
}

struct NFTTag_Previews: PreviewProvider {
    static var previews: some View {
        NFTTag()
    }
}
