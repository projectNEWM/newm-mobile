import Foundation

enum MainViewModelTab: CaseIterable {
    case library
	case profile
}

//TODO: localize
extension MainViewModelTab: CustomStringConvertible, Identifiable {
	var description: String {
		switch self {
		case .profile: return "Profile"
        case .library: return "Library"
		}
	}
	
	var id: Self { self }
}

extension MainViewModelTab: Hashable {
	func hash(into hasher: inout Hasher) {
		hasher.combine(description)
	}
}
