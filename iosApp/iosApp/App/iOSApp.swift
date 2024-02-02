import SwiftUI
import Resolver
import ModuleLinker
import UIKit
import shared
import Kingfisher
import Utilities

@main
struct iOSApp: App {
	private let mainViewProvider: MainViewProviding
	private var imageErrorPlugin = KingfisherErrorHandler()

	init() {
		KoinKt.doInitKoin(enableNetworkLogs: true)
#if DEBUG
		UserDefaults.standard.set(false, forKey: "_UIConstraintBasedLayoutLogUnsatisfiable")
#endif
		mainViewProvider = Resolver.resolve()
		
		setUpAppearance()
		setUpKingfisherErrorHandling()
	}
	
	var body: some Scene {
		WindowGroup {
			VStack {
				mainViewProvider.mainView()
			}
			.preferredColorScheme(.dark)
		}
	}
	
	private func setUpAppearance() {
		UINavigationBar.appearance().tintColor = .white
		let barAppearance = UIBarAppearance()
		barAppearance.configureWithOpaqueBackground()
		UITabBar.appearance().standardAppearance = UITabBarAppearance(barAppearance: barAppearance)
	}
}

extension iOSApp {
	func setUpKingfisherErrorHandling() {
		KingfisherManager.shared.downloader.delegate = imageErrorPlugin
	}
}
