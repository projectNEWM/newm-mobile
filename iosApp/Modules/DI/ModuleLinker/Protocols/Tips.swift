import Foundation
import SwiftUI

public protocol TipViewProviding {
	func tipView(tipSelected: @escaping (TipAmount) -> ()) -> AnyView
}

public enum TipAmount: CaseIterable, CustomStringConvertible, Identifiable {
	case fiveCents
	case tenCents
	case twentyCents
	case fiftyCents
	case oneDollar
	case twoDollars
	
	public var description: String {
		switch self {
		case .fiveCents: return "5¢"
		case .tenCents: return "10¢"
		case .twentyCents: return "20¢"
		case .fiftyCents: return "50¢"
		case .oneDollar: return "$1"
		case .twoDollars: return "$2"
		}
	}
	
	public var id: ObjectIdentifier { description.objectIdentifier }
}
