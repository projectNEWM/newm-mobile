import SwiftUI

struct GradientTag: View {
	let title: CustomStringConvertible
	
    var body: some View {
		Text(title.description)
			.font(.caption3.weight(.bold))
			.padding([.top, .bottom], 4)
			.padding([.leading, .trailing], 10)
			.background(LinearGradient(colors: [.green, .blue], startPoint: .top, endPoint: .bottom))
			.cornerRadius(20)
    }
}

struct GradientTag_Previews: PreviewProvider {
    static var previews: some View {
		GradientTag(title: "NFT")
    }
}
