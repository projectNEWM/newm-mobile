import SwiftUI
import UIKit

struct HeaderImageSection: View {
	private let model: HeaderImageCellModel
	
	init(_ model: HeaderImageCellModel) {
		self.model = model
	}
	//TODO: parallax header hidden behind nav bar
	var body: some View {
		GeometryReader { geometry in
			ZStack {
				if geometry.frame(in: .global).minY <= 0 {
					Image("\(model.headerImage)")
						.resizable()
//						.aspectRatio(16/9, contentMode: .fill)
						.aspectRatio(contentMode: .fill)
						.frame(height: geometry.size.height)
						.offset(y: geometry.frame(in: .global).minY/9)
						.clipped()
				} else {
					Image("\(model.headerImage)")
						.resizable()
//						.aspectRatio(16/9, contentMode: .fill)
						.aspectRatio(contentMode: .fill)
						.frame(width: geometry.size.width, height: geometry.size.height + geometry.frame(in: .global).minY)
//						.padding(.top, UIScreen.main.bounds.size.height/10)
						.clipped()
						.offset(y: -geometry.frame(in: .global).minY)
				}
			}
		}
		.frame(height: 20)
	}
}

struct HeaderImageSection_Previews: PreviewProvider {
	static var previews: some View {
		HeaderImageSection(HeaderImageCellModel(headerImage: "bowie"))
	}
}
