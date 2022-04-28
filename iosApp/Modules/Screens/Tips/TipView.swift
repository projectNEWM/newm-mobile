import SwiftUI
import ModuleLinker
import Resolver

public struct TipView: View {
	let tipSelected: (TipAmount) -> ()
	@Localizable var chooseYourTipPrompt = "CHOOSE_YOUR_TIP"
	@Injected private var circularProvider: CircularProviding
	
	public init(tipSelected: @escaping (TipAmount) -> ()) {
		self.tipSelected = tipSelected
	}
	
	public var body: some View {
		VStack {
			Text(chooseYourTipPrompt)
				.font(.largeTitle)
				.fontWeight(.heavy)
				.padding(.bottom, 70)
			circularProvider.circular {
				ForEach(TipAmount.allCases) { tipAmount in
					Button {
						tipSelected(tipAmount)
					} label: {
						Color.teal
							.frame(width: 75, height: 75)
							.clipShape(Circle())
							.overlay(Text(tipAmount.description).font(.headline).foregroundColor(.white).allowsTightening(true))
							.shadow(radius: 3)
					}
				}
			}
		}
		.frame(maxWidth: .infinity, maxHeight: .infinity)
		.background(.ultraThinMaterial)
	}
}

struct TipView_Previews: PreviewProvider {
	static var previews: some View {
		TipView(tipSelected: { _ in })
			.preferredColorScheme(.dark)
	}
}
