import Foundation
import SwiftUI

public extension Font {
	static func ralewayBlack(ofSize size: CGFloat) -> Font {
		.custom("RalewayRoman-Black", size: size)
	}
	
	static func raleway(ofSize size: CGFloat) -> Font {
		.custom("RalewayRoman-Regular", size: size)
	}
	
	static func ralewayExtraBold(ofSize size: CGFloat) -> Font {
		.custom("RalewayRoman-ExtraBold", size: size)
	}
	
	static func roboto(ofSize size: CGFloat) -> Font {
		.custom("Roboto-Regular", size: size)
	}
	
	static func robotoMedium(ofSize size: CGFloat) -> Font {
		.custom("Roboto-Medium", size: size)
	}
	
	static func inter(ofSize size: CGFloat) -> Font {
		.custom("Inter", size: size)
	}
	
	static func interMedium(ofSize size: CGFloat) -> Font {
		.custom("Inter", size: size).weight(.medium)
	}
	
	static func dmSerifItalic(ofSize size: CGFloat) -> Font {
		.custom("DMSerifText-Italic", size: size)
	}
	
	static var thisWeekCellAmountFont: Font {
		var fontDescriptor = UIFont(name: "RalewayRoman-Medium", size: 20)!.fontDescriptor
		var fontFeatures = [
			[
				UIFontDescriptor.FeatureKey.featureIdentifier: kNumberSpacingType,
				UIFontDescriptor.FeatureKey.typeIdentifier: kProportionalNumbersSelector,
			],
			[
				UIFontDescriptor.FeatureKey.featureIdentifier: kNumberCaseType,
				UIFontDescriptor.FeatureKey.typeIdentifier: kUpperCaseNumbersSelector,
			],
		]
		
		fontDescriptor = fontDescriptor.addingAttributes([UIFontDescriptor.AttributeName.featureSettings: fontFeatures])
		return Font(UIFont(descriptor: fontDescriptor, size: 20))
	}
}

public extension Font {
	static let caption2: Font = .custom("caption2", fixedSize: 12)
	static let caption3: Font = .custom("caption3", fixedSize: 11)
	static let caption4: Font = .custom("caption4", fixedSize: 10)
    static let newmTitle1: Font = .ralewayExtraBold(ofSize: 32)
}

