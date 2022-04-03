import SwiftUI
import SharedUI
import Utilities

public struct TipView: View {
	let tipSelected: (TipAmount) -> ()
	@Localizable var chooseYourTipPrompt = "CHOOSE_YOUR_TIP"
	
	public init(tipSelected: @escaping (TipAmount) -> ()) {
		self.tipSelected = tipSelected
	}
	
	public var body: some View {
		VStack {
			Text(chooseYourTipPrompt)
				.font(.largeTitle)
				.fontWeight(.heavy)
				.padding(.bottom, 70)
			Circular {
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

public enum TipAmount: CaseIterable, CustomStringConvertible, Identifiable {
	case fiveCents
	case tenCents
	case twentyCents
	case fiftyCents
	case oneDollar
	case twoDollars
	
	public var description: String {
		switch self {
		case .fiveCents: return "5¢"
		case .tenCents: return "10¢"
		case .twentyCents: return "20¢"
		case .fiftyCents: return "50¢"
		case .oneDollar: return "$1"
		case .twoDollars: return "$2"
		}
	}
	
	public var id: ObjectIdentifier { description.objectIdentifier }
}

struct TipView_Previews: PreviewProvider {
	static var previews: some View {
		TipView(tipSelected: { _ in })
			.preferredColorScheme(.dark)
	}
}
