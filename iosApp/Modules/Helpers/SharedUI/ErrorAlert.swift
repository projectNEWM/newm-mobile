import Foundation
import SwiftUI
import Utilities

struct NEWMAlert: ViewModifier {
	let title: String?
	let message: String?
	let onDismiss: () -> ()
	
	func body(content: Content) -> some View {
		content
			.alert(title.emptyIfNil, isPresented: .constant(true), actions: {
				Button {
					onDismiss()
				} label: {
					Text("Ok")
				}
			}, message: {
				Text(message.emptyIfNil)
			})
	}
}

public extension View {
	@ViewBuilder
	func newmAlert(title: String? = nil, message: String? = nil, onDismiss: @escaping () -> () = {}) -> some View {
		if title == nil, message == nil {
			self
		} else {
			modifier(NEWMAlert(title: title, message: message, onDismiss: onDismiss))
		}
	}
	
	@ViewBuilder
	func errorAlert(message: String?, onDismiss: @escaping () -> () = {}) -> some View {
		newmAlert(title: message != nil ? "Error" : nil, message: message, onDismiss: onDismiss)
	}
}
