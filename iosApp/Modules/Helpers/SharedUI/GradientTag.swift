import SwiftUI
import ModuleLinker
import Resolver

struct GradientTag: View {
	let title: CustomStringConvertible
	@Injected private var fontProvider: FontProviding
	@Injected private var colorProvider: ColorProviding
	
	public init(title: CustomStringConvertible) {
		self.title = title
	}
	
    public var body: some View {
		Text(title.description)
			.font(fontProvider.newmFont(ofSize: 11).bold())
			.padding([.top, .bottom], 4)
			.padding([.leading, .trailing], 10)
			.background(LinearGradient(colors: [colorProvider.color(for: .newmGreen), colorProvider.color(for: .newmLightBlue)], startPoint: .top, endPoint: .bottom))
			.cornerRadius(20)
			.foregroundColor(.black)
    }
}

struct GradientTag_Previews: PreviewProvider {
    static var previews: some View {
		GradientTag(title: "NFT")
			.preferredColorScheme(.dark)
    }
}
