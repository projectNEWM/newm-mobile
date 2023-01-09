import SwiftUI

enum ColorError: Error {
	case invalidHex
	case hexScanFailed
}

public extension Color {
	init(hex: String) throws {
		var hexSanitized = hex.trimmingCharacters(in: .whitespacesAndNewlines)
		hexSanitized = hexSanitized.replacingOccurrences(of: "#", with: "")

		var rgb: UInt64 = 0

		var r: CGFloat = 0.0
		var g: CGFloat = 0.0
		var b: CGFloat = 0.0
		var a: CGFloat = 1.0

		let length = hexSanitized.count

		guard Scanner(string: hexSanitized).scanHexInt64(&rgb) else { throw ColorError.hexScanFailed }

		if length == 6 {
			r = CGFloat((rgb & 0xFF0000) >> 16) / 255.0
			g = CGFloat((rgb & 0x00FF00) >> 8) / 255.0
			b = CGFloat(rgb & 0x0000FF) / 255.0
		} else if length == 8 {
			r = CGFloat((rgb & 0xFF000000) >> 24) / 255.0
			g = CGFloat((rgb & 0x00FF0000) >> 16) / 255.0
			b = CGFloat((rgb & 0x0000FF00) >> 8) / 255.0
			a = CGFloat(rgb & 0x000000FF) / 255.0
		} else {
			throw ColorError.invalidHex
		}

		self.init(red: r, green: g, blue: b, opacity: a)
	}
}

public extension Array where Element == String {
	var colors: [Color] {
		try! map(Color.init(hex:))
	}
}

public extension Array where Element == ColorAsset {
	var hexStrings: [String] {
		map(\.hexString)
	}
}

public extension ColorAsset {
	var hexString: String {
		let components = color.cgColor.components
		let r: CGFloat = components?[0] ?? 0.0
		let g: CGFloat = components?[1] ?? 0.0
		let b: CGFloat = components?[2] ?? 0.0
		
		let hexString = String.init(format: "#%02lX%02lX%02lX", lroundf(Float(r * 255)), lroundf(Float(g * 255)), lroundf(Float(b * 255)))
		return hexString
	}
}
