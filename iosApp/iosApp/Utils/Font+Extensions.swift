import Foundation
import SwiftUI

extension Font {
	static let caption3: Font = .custom("caption3", fixedSize: 11)
	static let caption4: Font = .custom("caption4", fixedSize: 10)
}

extension Font {
	static func newmFontBold(ofSize size: CGFloat) -> Font {
		custom("RalewayRoman-Black", size: size)
	}
	
	static func newmFont(ofSize size: CGFloat) -> Font {
		custom("RalewayRoman-Regular", size: size)
	}
	
	static func roboto(ofSize size: CGFloat) -> Font {
		custom("Roboto-Regular", size: size)
	}
}
