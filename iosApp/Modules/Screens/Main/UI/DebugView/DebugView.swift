import Foundation
import SwiftUI
import shared
import Resolver
import ModuleLinker

//TODO: remove from app store builds
struct DebugView: View {
	var body: some View {
		List {
			Button {
				Resolver.resolve(LoginUseCase.self).logout()
			} label: {
				Text("Log out")
			}
			
			if let appVersion = Bundle.main.infoDictionary?["CFBundleShortVersionString"] as? String {
				Text("App version: \(appVersion)")
			}
			
			Button {
				fatalError("Debug view crash")
			} label: {
				Text("Crash")
			}
			
			Button {
				Resolver.resolve(ErrorReporting.self).logError("Test error")
			} label: {
				Text("Send test sentry error")
			}
		}
	}
}
