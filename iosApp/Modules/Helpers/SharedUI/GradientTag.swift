import SwiftUI
import ModuleLinker
import Resolver
import Fonts
import Colors

struct GradientTag: View {
	let title: CustomStringConvertible
	
	public init(title: CustomStringConvertible) {
		self.title = title
	}
	
    public var body: some View {
		Text(title.description)
			.font(.raleway(ofSize: 11).bold())
			.padding([.top, .bottom], 4)
			.padding([.leading, .trailing], 10)
			.background(LinearGradient(colors: [NEWMColor.green(),
												NEWMColor.lightBlue()],
									   startPoint: .top,
									   endPoint: .bottom))
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
