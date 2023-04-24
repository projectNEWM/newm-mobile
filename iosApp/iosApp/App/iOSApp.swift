import SwiftUI
import Resolver
import ModuleLinker
import shared
import FacebookCore

@main
struct iOSApp: App {
	let mainViewProvider: MainViewProviding
	
	init() {
		KoinKt.doInitKoin()
#if DEBUG
		UserDefaults.standard.set(false, forKey: "_UIConstraintBasedLayoutLogUnsatisfiable")
#endif
		mainViewProvider = Resolver.resolve()
		
		setUpAppearance()
		
		FBSDKCoreKit.ApplicationDelegate.shared.application(
			UIApplication.shared,
			didFinishLaunchingWithOptions: [:]
		)
	}
	
	var body: some Scene {
		WindowGroup {
			VStack {
				mainViewProvider.mainView()
			}
			.preferredColorScheme(.dark)
//			.onOpenURL { url in
//				ApplicationDelegate.shared.application(
//							UIApplication.shared,
//							open: url,
//							sourceApplication: nil,
//							annotation: [UIApplication.OpenURLOptionsKey.annotation]
//						)
//			}
		}
	}
	
	private func setUpAppearance() {
		UINavigationBar.appearance().tintColor = .white
		let barAppearance = UIBarAppearance()
		barAppearance.configureWithOpaqueBackground()
		UITabBar.appearance().standardAppearance = UITabBarAppearance(barAppearance: barAppearance)
	}
}
