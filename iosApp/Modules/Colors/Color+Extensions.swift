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
	init(hex: String) {
		let hex = hex.trimmingCharacters(in: CharacterSet.alphanumerics.inverted)
		var int: UInt64 = 0
		Scanner(string: hex).scanHexInt64(&int)
		let a, r, g, b: UInt64
		switch hex.count {
		case 3: // RGB (12-bit)
			(a, r, g, b) = (255, (int >> 8) * 17, (int >> 4 & 0xF) * 17, (int & 0xF) * 17)
		case 6: // RGB (24-bit)
			(a, r, g, b) = (255, int >> 16, int >> 8 & 0xFF, int & 0xFF)
		case 8: // ARGB (32-bit)
			(a, r, g, b) = (int >> 24, int >> 16 & 0xFF, int >> 8 & 0xFF, int & 0xFF)
		default:
			(a, r, g, b) = (1, 1, 1, 0)
		}
		
		self.init(
			.sRGB,
			red: Double(r) / 255,
			green: Double(g) / 255,
			blue:  Double(b) / 255,
			opacity: Double(a) / 255
		)
	}
	
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
