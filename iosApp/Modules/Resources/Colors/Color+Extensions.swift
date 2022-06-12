import SwiftUI
import ModuleLinker

extension ColorsModule: ColorProviding {
	public func color(for newmColor: NEWMColor) -> Color {
		switch newmColor {
		case .newmLightBlue:
			return Color(.newmLightBlue)
		case .newmBlue:
			return Color(.newmBlue)
		case .newmPurple:
			return Color(.newmPurple)
		case .newmPink:
			return Color(.newmPink)
		case .newmRed:
			return Color(.newmRed)
		case .newmOrange:
			return Color(.newmOrange)
		case .newmYellow:
			return Color(.newmYellow)
		case .newmGreen:
			return Color(.newmGreen)
		case .newmOffPink:
			return Color(.newmOffPink)
		case .grey100:
			return Color(.grey100)
		case .grey600:
			return Color(.grey600)
		case .newmPurple2:
			return Color(.newmPurple2)
		}
	}
}

public extension Color {
	init(_ newmColor: NEWMColor) {
		self = {
			switch newmColor {
			case .newmLightBlue:
				return Color(value: NEWMColor.newmLightBlue.rawValue)
			case .newmBlue:
				return Color(value: NEWMColor.newmBlue.rawValue)
			case .newmPurple:
				return Color(value: NEWMColor.newmPurple.rawValue)
			case .newmPink:
				return Color(value: NEWMColor.newmPink.rawValue)
			case .newmRed:
				return Color(value: NEWMColor.newmRed.rawValue)
			case .newmOrange:
				return Color(value: NEWMColor.newmOrange.rawValue)
			case .newmYellow:
				return Color(value: NEWMColor.newmYellow.rawValue)
			case .newmGreen:
				return Color(value: NEWMColor.newmGreen.rawValue)
			case .newmOffPink:
				return Color(value: NEWMColor.newmOffPink.rawValue)
			case .grey100:
				return Color(value: NEWMColor.grey100.rawValue)
			case .grey600:
				return Color(value: NEWMColor.grey600.rawValue)
			case .newmPurple2:
				return Color(value: NEWMColor.newmPurple2.rawValue)
			}
		}()
	}
	
	init(value: String) {
		self = Color(value, bundle: Bundle(for: ColorsModule.self))
	}
}
