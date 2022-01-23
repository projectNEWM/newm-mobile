//
//  MainView.swift
//  iosApp
//
//  Created by Marty Ulrich on 1/6/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct MainView: View {
	@State private var viewModel = MainViewModel()
	
	init() {
		UITabBar.appearance(for: .init(userInterfaceStyle: .light)).backgroundColor = .black
		UITabBar.appearance(for: .init(userInterfaceStyle: .light)).barTintColor = .white
	}
	
	var body: some View {
		TabView {
			HomeView()
				.tabItem {
					Image(MainViewModel.Tab.home)
					Text(MainViewModel.Tab.home)
				}
			TribeView()
				.tabItem {
					Image(MainViewModel.Tab.tribe)
					Text(MainViewModel.Tab.tribe)
				}
			StarsView()
				.tabItem {
					Image(MainViewModel.Tab.stars)
					Text(MainViewModel.Tab.stars)
				}
			WalletView()
				.tabItem {
					Image(MainViewModel.Tab.wallet)
					Text(MainViewModel.Tab.wallet)
				}
		}
		.accentColor(.white)
		.background(Color.black)
	}
}

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

private extension Image {
	init(_ tab: MainViewModel.Tab) {
		switch tab {
		case .home: self = Image("Home Icon")
		case .stars: self = Image("Stars Icon")
		case .tribe: self = Image("Community Icon")
		case .wallet: self = Image("Wallet Icon")
		}
	}
}

private extension Text {
	init(_ tab: MainViewModel.Tab) {
		self = Text(tab.description)
	}
}

struct MainView_Previews: PreviewProvider {
	static var previews: some View {
		MainView()
	}
}
