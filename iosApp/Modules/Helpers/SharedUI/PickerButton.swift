import SwiftUI
import Colors

public struct PickerButton: View {
	private let label: String
	
	public init(label: String) {
		self.label = label
	}
	
    public var body: some View {
		HStack(spacing: 2) {
			Text(label).font(.inter(ofSize: 12).bold())
			Asset.Media.arrowSmallDown.swiftUIImage
		}
		.padding(.leading, 18)
		.padding(.trailing, 12)
		.padding([.top, .bottom], 8)
		.background(NEWMColor.grey600())
		.cornerRadius(1000)
    }
}

struct PickerButton_Previews: PreviewProvider {
    static var previews: some View {
        PickerButton(label: "$")
			.preferredColorScheme(.dark)
    }
}
