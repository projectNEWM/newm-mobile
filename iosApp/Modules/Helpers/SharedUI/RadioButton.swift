import SwiftUI
import Colors
import Fonts

public struct RadioButton<Option: Equatable & RadioOption>: View, RadioButtonProtocol {
	public init(_ option: Option, selectedOption: Binding<Option?>) {
		self.option = option
		self._selectedOption = selectedOption
	}
	
	let option: Option
	@Binding var selectedOption: Option?
	
	public var body: some View {
		Button(action: {
			selectedOption = option
		}) {
			if selectedOption == option {
				baseBody
					.background(
						LinearGradient(gradient: Gradient(colors: [NEWMColor.orange2(), NEWMColor.orange1()]),
									   startPoint: .topLeading,
									   endPoint: .bottomTrailing)
					)
			} else {
				baseBody
					.overlay(
						RoundedRectangle(cornerRadius: 4)
							.stroke(NEWMColor.grey500(), lineWidth: 2)
					)
					.background(NEWMColor.grey600())
			}
		}
		.cornerRadius(4)
		.font(.interMedium(ofSize: 14))
	}
	
	private var baseBody: some View {
		HStack(alignment: .center) {
			Text(option.description)
			Spacer()
		}
		.padding()
		.foregroundColor(.white)
		.frame(height: 36)
	}
}

struct RadioPicker_Previews: PreviewProvider {
	struct RadioPickerManager: View {
		@State var option: String = "Option 1"
		
		var body: some View {
			RadioPicker(options: ["Option 1", "Option 2", "Option 3", "Option 4", "Option 5", "Option 6"],
						selectedOption: $option,
						RadioButtonType: RadioButton.self)
		}
	}
	
	static var previews: some View {
		RadioPickerManager()
			.preferredColorScheme(.dark)
	}
}
