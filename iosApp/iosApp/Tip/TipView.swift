import SwiftUI

struct TipView: View {
	let tipSelected: (TipAmount) -> ()
	let chooseYourTipPrompt: String = NSLocalizedString("CHOOSE_YOUR_TIP", comment: "")
	
	var body: some View {
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

enum TipAmount: CaseIterable, CustomStringConvertible, Identifiable {
	case fiveCents
	case tenCents
	case twentyCents
	case fiftyCents
	case oneDollar
	case twoDollars
	
	var description: String {
		switch self {
		case .fiveCents: return "5¢"
		case .tenCents: return "10¢"
		case .twentyCents: return "20¢"
		case .fiftyCents: return "50¢"
		case .oneDollar: return "$1"
		case .twoDollars: return "$2"
		}
	}
	
	var id: ObjectIdentifier { description.objectIdentifier }
}

struct TipView_Previews: PreviewProvider {
	static var previews: some View {
		TipView(tipSelected: { _ in })
			.preferredColorScheme(.dark)
	}
}
