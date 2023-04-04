import SwiftUI
import Colors
import Fonts

public struct RadioButton<Option: Equatable & RadioOption>: View, RadioButtonProtocol {
	let option: Option
	@Binding var selectedOption: Option?
	private let gradient: [String]
	
	public init(_ option: Option, selectedOption: Binding<Option?>, gradient: [String]) {
		self.option = option
		self._selectedOption = selectedOption
		self.gradient = gradient
	}
	
	public var body: some View {
		Button(action: {
			selectedOption = option
		}) {
			if selectedOption == option {
				baseBody.background(gradient.gradient)
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

struct RadioButton_Previews: PreviewProvider {
	struct RadioPickerManager: View {
		@State var option: String = "Option 1"
		
		var body: some View {
			RadioPicker(options: ["Option 1", "Option 2", "Option 3", "Option 4", "Option 5", "Option 6"],
						selectedOption: $option,
						RadioButtonType: RadioButton.self,
						gradient: [NEWMColor.orange.hexString, NEWMColor.orange1.hexString])
		}
	}
	
	static var previews: some View {
		RadioPickerManager()
			.preferredColorScheme(.dark)
	}
}
