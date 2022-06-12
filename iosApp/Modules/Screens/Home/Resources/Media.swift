import Foundation
import SwiftUI

enum Icon {
	case heart
	case royalties
	case earnings
	
	var image: Image {
		switch self {
		case .heart: return Image("HeartIcon", bundle: Bundle(for: HomeModule.self))
		case .royalties: return Image("RoyaltiesIcon", bundle: Bundle(for: HomeModule.self))
		case .earnings: return Image("EarningsIcon", bundle: Bundle(for: HomeModule.self))
		}
	}
}
