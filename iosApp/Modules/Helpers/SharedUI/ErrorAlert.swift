import Foundation
import SwiftUI
import Utilities

public struct AlertInfo {
	public let title: String?
	public let message: String?
	
	public init(title: String?, message: String?) {
		precondition(title != nil || message != nil)
		self.title = title
		self.message = message
	}
}

struct NEWMAlert: ViewModifier {
	let info: AlertInfo
	let onDismiss: () -> ()
	
	func body(content: Content) -> some View {
		
		content
			.alert(info.title.emptyIfNil, isPresented: .constant(true), actions: {
				Button {
					onDismiss()
				} label: {
					Text("Ok")
				}
			}, message: {
				Text(info.message.emptyIfNil)
			})
	}
}

public extension View {
	@ViewBuilder
	func newmAlert(info: AlertInfo?, onDismiss: @escaping () -> () = {}) -> some View {
		if let info {
			modifier(NEWMAlert(info: info, onDismiss: onDismiss))
		} else {
			self
		}
	}
	
	@ViewBuilder
	func errorAlert(message: String?, onDismiss: @escaping () -> () = {}) -> some View {
		if let message {
			modifier(NEWMAlert(info: AlertInfo(title: "Error", message: message), onDismiss: onDismiss))
		} else {
			self
		}
	}
}
