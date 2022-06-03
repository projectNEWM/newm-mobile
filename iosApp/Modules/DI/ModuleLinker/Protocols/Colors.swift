import Foundation
import SwiftUI

public enum NEWMColor: String, CaseIterable {
	case newmLightBlue
	case newmBlue
	case newmPurple
	case newmPink
	case newmRed
	case newmOrange
	case newmYellow
	case newmGreen
	case newmOffPink
	case grey100
}

public protocol ColorProviding {
	func color(for newmColor: NEWMColor) -> Color
}
