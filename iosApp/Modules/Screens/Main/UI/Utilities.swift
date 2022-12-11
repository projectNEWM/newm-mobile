import SwiftUI

class ModuleClass {}

extension Image {
	init(_ tab: MainViewModelTab) {
		switch tab {
		case .home: self = Image("Home Icon", bundle: Bundle(for: ModuleClass.self))
        case .library: self = Image("Library Icon", bundle: Bundle(for: ModuleClass.self))
		case .wallet: self = Image("Wallet Icon", bundle: Bundle(for: ModuleClass.self))
		case .marketplace: self = Image("Marketplace Icon", bundle: Bundle(for: ModuleClass.self))
		case .more: self = Image(systemName: "ellipsis")
		}
	}
}

extension Image {
	init(_ tab: MainViewModelTab.More) {
		switch tab {
		case .playlists: self = Image(systemName: "Wallet Icon")
		case .artists: self = Image(systemName: "Wallet Icon")
		case .profile: self = Image(systemName: "Wallet Icon")
		case .search: self = Image(systemName: "Wallet Icon")
		case .genres: self = Image(systemName: "Wallet Icon")
		}
	}
}
