import SwiftUI

class ModuleClass {}

extension Image {
	init(_ tab: MainViewModelTab) {
		switch tab {
        case .library: self = Image("Library Icon", bundle: Bundle(for: ModuleClass.self))
		case .profile: self = Image("Profile Icon", bundle: Bundle(for: ModuleClass.self))
		}
	}
}
