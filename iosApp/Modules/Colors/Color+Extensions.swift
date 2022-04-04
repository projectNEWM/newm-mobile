import SwiftUI

class ModuleClass {}

public extension Color {
	static var newmLightBlue: Color { Color(.newmLightBlue) }
	static var newmBlue: Color { Color(.newmBlue) }
	static var newmPurple: Color { Color(.newmPurple) }
	static var newmPink: Color { Color(.newmPink) }
	static var newmRed: Color { Color(.newmRed) }
	static var newmOrange: Color { Color(.newmOrange) }
	static var newmYellow: Color { Color(.newmYellow) }
	static var newmGreen: Color { Color(.newmGreen) }
	static var newmOffPink: Color { Color(.newmOffPink) }
}

extension Color {
	public init(_ newmColor: NEWMColor) {
		self = {
			switch newmColor {
			case .newmLightBlue:
				return Color(NEWMColor.newmLightBlue.rawValue, bundle: Bundle(for: ModuleClass.self))
			case .newmBlue:
				return Color(NEWMColor.newmBlue.rawValue, bundle: Bundle(for: ModuleClass.self))
			case .newmPurple:
				return Color(NEWMColor.newmPurple.rawValue, bundle: Bundle(for: ModuleClass.self))
			case .newmPink:
				return Color(NEWMColor.newmPink.rawValue, bundle: Bundle(for: ModuleClass.self))
			case .newmRed:
				return Color(NEWMColor.newmRed.rawValue, bundle: Bundle(for: ModuleClass.self))
			case .newmOrange:
				return Color(NEWMColor.newmOrange.rawValue, bundle: Bundle(for: ModuleClass.self))
			case .newmYellow:
				return Color(NEWMColor.newmYellow.rawValue, bundle: Bundle(for: ModuleClass.self))
			case .newmGreen:
				return Color(NEWMColor.newmGreen.rawValue, bundle: Bundle(for: ModuleClass.self))
			case .newmOffPink:
				return Color(NEWMColor.newmOffPink.rawValue, bundle: Bundle(for: ModuleClass.self))
			}
		}()
	}
}
