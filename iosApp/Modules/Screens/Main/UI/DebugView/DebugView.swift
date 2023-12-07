import Foundation
import SwiftUI
import shared
import Resolver

//TODO: remove from app store builds
struct DebugView: View {
	@Injected private var logOutUseCase: UserSessionUseCase
	
	var body: some View {
		List {
			Button {
				logOutUseCase.logout()
			} label: {
				Text("Log out")
			}
			
			if let appVersion = Bundle.main.infoDictionary?["CFBundleShortVersionString"] as? String {
				Text("App version: \(appVersion)")
			}
		}
	}
}
