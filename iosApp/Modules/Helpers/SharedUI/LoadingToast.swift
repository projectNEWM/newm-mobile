import SwiftUI

public struct LoadingToast: View {
	public init() {}
	
	public var body: some View {
		ProgressView()
			.frame(width: 100, height: 100)
			.background(.gray)
			.opacity(0.5)
			.cornerRadius(10)
			.foregroundColor(.white)
	}
}

struct LoadingToast_Previews: PreviewProvider {
	static var previews: some View {
		LoadingToast()
	}
}
