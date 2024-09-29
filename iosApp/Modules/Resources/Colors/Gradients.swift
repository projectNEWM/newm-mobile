import Foundation
import SwiftUI

public struct Gradients {
	public static var marketplaceGradient: [String] {
		[NEWMColor.orange2, NEWMColor.orange1].hexStrings
	}
	
	public static var homeTitleGradient: [String] {
		[NEWMColor.lightBlue, NEWMColor.purple2].hexStrings
	}
	
	public static var libraryGradient: [String] {
		[NEWMColor.main, NEWMColor.primary].hexStrings
	}
	
	public static var walletGradient: [String] {
		[NEWMColor.blue2, NEWMColor.green2].hexStrings
	}
	
	public static var loginGradient: [String] {
		[NEWMColor.purple, NEWMColor.pink].hexStrings
	}
	
	public static var mainPrimary: LinearGradient {
		LinearGradient(
			stops: [
				Gradient.Stop(color: NEWMColor.main.swiftUIColor, location: 0.00),
				Gradient.Stop(color: NEWMColor.primary.swiftUIColor, location: 1.00),
			],
			startPoint: UnitPoint(x: 0, y: 1),
			endPoint: UnitPoint(x: 1, y: 0)
		)
	}
	
	public static var mainPrimaryLight: LinearGradient {
		LinearGradient(
			stops: [
				Gradient.Stop(color: NEWMColor.main.swiftUIColor.opacity(0.08), location: 0.00),
				Gradient.Stop(color: NEWMColor.primary.swiftUIColor.opacity(0.08), location: 1.00),
			],
			startPoint: UnitPoint(x: 0, y: 1),
			endPoint: UnitPoint(x: 1, y: 0)
		)
	}
	
	public static var mainSecondary: LinearGradient {
		LinearGradient(
			stops: [
				Gradient.Stop(color: try! Color(hex: "41BE91"), location: 0),
				Gradient.Stop(color: try! Color(hex: "5091EB"), location: 1)
			],
			startPoint: UnitPoint(x: 0, y: 1),
			endPoint: UnitPoint(x: 1, y: 0)
		)
	}
	
	public static var mainSecondaryLight: LinearGradient {
		LinearGradient(
			stops: [
				Gradient.Stop(color: try! Color(hex: "41BE91").opacity(0.08), location: 0),
				Gradient.Stop(color: try! Color(hex: "5091EB").opacity(0.08), location: 1)
			],
			startPoint: UnitPoint(x: 0, y: 1),
			endPoint: UnitPoint(x: 1, y: 0)
		)
	}
}

public extension Array where Element == String {
	var gradient: LinearGradient {
		LinearGradient(colors: colors, startPoint: .leading, endPoint: .trailing)
	}
}

#Preview {
	Gradients.mainPrimary
}

#Preview {
	Gradients.mainSecondary
}

#Preview {
	Gradients.mainSecondaryLight
}
