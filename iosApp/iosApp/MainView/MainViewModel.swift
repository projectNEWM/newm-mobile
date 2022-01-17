//
//  MainViewModel.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/16/22.
//

import Foundation

class MainViewModel: ObservableObject {
	enum Tab {
		case home
		case tribe
		case stars
		case wallet
	}
	
	@Published var selectedTab: Tab = .home
}

extension MainViewModel.Tab: CustomStringConvertible {
	var description: String {
		switch self {
		case .home: return "Home"
		case .tribe: return "Tribe"
		case .stars: return "Stars"
		case .wallet: return "Wallet"
		}
	}
}
