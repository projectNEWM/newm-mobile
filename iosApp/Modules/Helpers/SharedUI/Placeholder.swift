import UIKit
import SwiftUI

public extension UIImage {
	static var placeholder: UIImage? {
		UIImage(named: "placeholder", in: Bundle(for: SharedUIModule.self), with: nil)
	}
}

public extension Image {
	static var placeholder: Image? {
		UIImage.placeholder.flatMap(Image.init)
	}
}
