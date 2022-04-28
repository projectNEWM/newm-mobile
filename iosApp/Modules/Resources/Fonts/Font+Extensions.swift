import Foundation
import SwiftUI
import ModuleLinker


extension FontsModule: FontProviding {
	public func newmFontBold(ofSize size: CGFloat) -> Font {
		.custom("RalewayRoman-Black", size: size)
	}
	
	public func newmFont(ofSize size: CGFloat) -> Font {
		.custom("RalewayRoman-Regular", size: size)
	}
	
	public func roboto(ofSize size: CGFloat) -> Font {
		.custom("Roboto-Regular", size: size)
	}
	
	public func robotoMedium(ofSize size: CGFloat) -> Font {
		.custom("Roboto-Medium", size: size)
	}
}
