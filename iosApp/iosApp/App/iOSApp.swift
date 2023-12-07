import SwiftUI
import Resolver
import ModuleLinker
import UIKit
import shared

@main
struct iOSApp: App {
	let mainViewProvider: MainViewProviding
	
	init() {
		KoinKt.doInitKoin(enableNetworkLogs: true)
#if DEBUG
		UserDefaults.standard.set(false, forKey: "_UIConstraintBasedLayoutLogUnsatisfiable")
#endif
		mainViewProvider = Resolver.resolve()
		
		setUpAppearance()
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

//#if INTERNAL
//#if ENTERPRISE
//TODO: uncomment this for prod
extension UIWindow {
	open override func motionEnded(_ motion: UIEvent.EventSubtype, with event: UIEvent?) {
		if motion == .motionShake {
			if let appVersion = Bundle.main.infoDictionary?["CFBundleShortVersionString"] as? String {
				let alert = UIAlertController(title: "App version", message: appVersion, preferredStyle: .alert)
				alert.addAction(UIAlertAction(title: "Ok", style: .cancel))
				rootViewController?.present(alert, animated: true)
			} else {
				print("Unable to retrieve app version.")
			}
		}
	}
}
