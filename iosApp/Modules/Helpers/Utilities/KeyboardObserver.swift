import SwiftUI
import Combine
import UIKit

public class KeyboardObserver: ObservableObject {
	@Published public var isKeyboardShown = false
	
	public init() {
		NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillChangeFrame(_:)), name: UIResponder.keyboardWillChangeFrameNotification, object: nil)
	}
	
	@objc private func keyboardWillChangeFrame(_ notification: Notification) {
		if let keyboardFrame = notification.userInfo?[UIResponder.keyboardFrameEndUserInfoKey] as? CGRect {
			isKeyboardShown = keyboardFrame.origin.y < UIScreen.main.bounds.height
		}
	}
}
