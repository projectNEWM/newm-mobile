import Foundation
import SwiftUI
import SharedUI

enum SupFolIcon {
	case follow
	case support
	
	var image: Image {
		switch self {
		case .follow: return Image("Star Icon", bundle: Bundle(for: SharedUIModule.self))
		case .support: return Image("Heart Plus", bundle: Bundle(for: SharedUIModule.self))
		}
	}
}
