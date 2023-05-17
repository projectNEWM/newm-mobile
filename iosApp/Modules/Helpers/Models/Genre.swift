import Foundation

public enum Genre: CaseIterable {
	case rock
	case classical
	case rap
	case folk
	case country
		
	public var title: String {
		switch self {
		case .folk: return "Folk"
		case .classical: return "Classical"
		case .country: return "Country"
		case .rap: return "Rap"
		case .rock: return "Rock"
		}
	}
}
