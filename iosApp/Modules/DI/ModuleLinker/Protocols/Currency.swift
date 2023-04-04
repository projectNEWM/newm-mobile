import Foundation

public struct Currency: CaseIterable, Hashable {
	public static var allCases: [Currency] {
		Currencies.allCases.map { currency in
			switch currency {
			case .usd:
				return Currency(title: "US Dollars", symbol: "$")
			case .newm:
				return Currency(title: "NEWM Tokens", symbol: "Ɲ")
			case .ada:
				return Currency(title: "Cardano", symbol: "₳")
			}
		}
	}
	
	public let title: String
	public let symbol: String
}

enum Currencies: CaseIterable {
	case usd
	case newm
	case ada
}
