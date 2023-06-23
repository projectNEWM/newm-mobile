import SwiftUI
import UIKit

public struct HeaderImageSection: View {
	private let imageURL: String?
	
	public init(_ imageURL: String?) {
		self.imageURL = imageURL
	}
	//TODO: parallax header hidden behind nav bar
	public var body: some View {
		GeometryReader { geometry in
			ZStack {
				image(size: geometry.size, frame: geometry.frame(in: .global))
			}
		}
		.frame(height: 20)
	}
	
	@ViewBuilder
	private func image(size: CGSize, frame: CGRect) -> some View {
		let image = AsyncImage(
			url: imageURL.flatMap(URL.init),
			content: { image in
				image.resizable()
			}, placeholder: {
				Image.placeholder.resizable()
			})
			.aspectRatio(contentMode: .fill)
		
		if frame.minY <= 0 {
			image
				.frame(height: size.height)
				.offset(y: frame.minY/9)
				.clipped()
				.erased
		} else {
			image
				.frame(width: size.width, height: size.height + frame.minY)
				.clipped()
				.offset(y: -frame.minY)
				.erased
		}
	}
}

struct HeaderImageSection_Previews: PreviewProvider {
	static var previews: some View {
		HeaderImageSection("https://resizing.flixster.com/xhyRkgdbTuATF4u0C2pFWZQZZtw=/300x300/v2/https://flxt.tmsimg.com/assets/p175884_k_v9_ae.jpg")
	}
}
