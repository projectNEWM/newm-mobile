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
		[NEWMColor.purple3, NEWMColor.pink3].hexStrings
	}
	
	public static var walletGradient: [String] {
		[NEWMColor.blue2, NEWMColor.green2].hexStrings
	}
	
	public static var loginGradient: [String] {
		[NEWMColor.purple, NEWMColor.pink].hexStrings
	}
	
	public static var primaryPrimary: LinearGradient {
		LinearGradient(
			stops: [
				Gradient.Stop(color: Color(red: 0.76, green: 0.25, blue: 0.94), location: 0.00),
				Gradient.Stop(color: Color(red: 0.96, green: 0.24, blue: 0.41), location: 1.00),
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
