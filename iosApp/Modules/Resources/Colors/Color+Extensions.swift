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
		}
	}
}

extension Color {
	public init(_ newmColor: NEWMColor) {
		self = {
			switch newmColor {
			case .newmLightBlue:
				return Color(NEWMColor.newmLightBlue.rawValue, bundle: Bundle(for: ColorsModule.self))
			case .newmBlue:
				return Color(NEWMColor.newmBlue.rawValue, bundle: Bundle(for: ColorsModule.self))
			case .newmPurple:
				return Color(NEWMColor.newmPurple.rawValue, bundle: Bundle(for: ColorsModule.self))
			case .newmPink:
				return Color(NEWMColor.newmPink.rawValue, bundle: Bundle(for: ColorsModule.self))
			case .newmRed:
				return Color(NEWMColor.newmRed.rawValue, bundle: Bundle(for: ColorsModule.self))
			case .newmOrange:
				return Color(NEWMColor.newmOrange.rawValue, bundle: Bundle(for: ColorsModule.self))
			case .newmYellow:
				return Color(NEWMColor.newmYellow.rawValue, bundle: Bundle(for: ColorsModule.self))
			case .newmGreen:
				return Color(NEWMColor.newmGreen.rawValue, bundle: Bundle(for: ColorsModule.self))
			case .newmOffPink:
				return Color(NEWMColor.newmOffPink.rawValue, bundle: Bundle(for: ColorsModule.self))
			}
		}()
	}
}
