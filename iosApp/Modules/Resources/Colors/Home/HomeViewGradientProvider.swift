import Foundation
import ModuleLinker
import SwiftUI

extension ColorsModule: HomeViewGradientProviding {
	public var gradientColors: [Color] {
		[
			Color(.newmLightBlue),
			Color(.newmBlue),
			Color(.newmPurple),
			Color(.newmPink),
			Color(.newmRed),
			Color(.newmOrange),
			Color(.newmYellow),
			Color(.newmGreen)
		]
	}
}
