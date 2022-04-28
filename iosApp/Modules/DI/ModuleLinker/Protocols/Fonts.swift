import Foundation
import SwiftUI

public protocol FontProviding {
	func newmFontBold(ofSize size: CGFloat) -> Font
	func newmFont(ofSize size: CGFloat) -> Font
	func roboto(ofSize size: CGFloat) -> Font
	func robotoMedium(ofSize size: CGFloat) -> Font
}

public enum Fonts {
	case roboto
}

public extension Font {
	static let caption3: Font = .custom("caption3", fixedSize: 11)
	static let caption4: Font = .custom("caption4", fixedSize: 10)
}
