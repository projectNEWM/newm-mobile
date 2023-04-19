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
}

public extension Array where Element == String {
	var gradient: LinearGradient {
		LinearGradient(colors: colors, startPoint: .leading, endPoint: .trailing)
	}
}
