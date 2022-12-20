import SwiftUI
import Colors

public struct SegmentedButton<Option: RadioOption>: View, RadioButtonProtocol {
	let option: Option
	@Binding var selectedOption: Option?
	let gradient: [String]

	public init(_ option: Option, selectedOption: Binding<Option?>, gradient: [String]) {
		self.option = option
		self._selectedOption = selectedOption
		self.gradient = gradient
	}
	
	public var body: some View {
		VStack {
			Text(option.description)
			gradient.gradient.frame(height: 2)
		}
		.onTapGesture {
			selectedOption = option
		}
    }
}

struct SegmentedButton_Previews: PreviewProvider {
	static var previews: some View {
		Group {
			SegmentedButton("Hi", selectedOption: .constant("Hi"), gradient: [NEWMColor.blue, NEWMColor.red].hexStrings)
			SegmentedButton("Bye", selectedOption: .constant(nil), gradient: [NEWMColor.blue, NEWMColor.red].hexStrings)
		}
		.preferredColorScheme(.dark)
	}
}

public struct SegmentedRadioPicker<Option, RadioButtonType: RadioButtonProtocol>: View where Option: RadioOption, Option == RadioButtonType.Option {
	private let options: [Option]
	@Binding public var selectedOption: Option
	public let gradient: [String]
	
	private let RadioButtonType: any RadioButtonProtocol.Type
	
	public init(options: [Option], selectedOption: Binding<Option>, RadioButtonType: RadioButtonType.Type, gradient: [String]) {
		self.options = options
		self._selectedOption = selectedOption
		self.RadioButtonType = RadioButtonType
		self.gradient = gradient
	}
	
	public var body: some View {
		HStack(alignment: .center, spacing: 0) {
			ForEach(options, id: \.self) { option in
				RadioButtonType(option, selectedOption: Binding<Option?>($selectedOption), gradient: selectedOption == option ? gradient : [NEWMColor.grey400.hexString])
			}
		}
	}
}

struct SegmentedRadioPicker_Previews: PreviewProvider {
	static let options = ["Option 1", "Option 2"]
	struct SegmentedRadioPickerManager<RadioButtonType: RadioButtonProtocol>: View where RadioButtonType.Option == String {
		@State var option: String = "Option 1"
		
		var body: some View {
			SegmentedRadioPicker(options: SegmentedRadioPicker_Previews.options,
								 selectedOption: $option,
								 RadioButtonType: SegmentedButton.self,
								 gradient: [NEWMColor.orange, NEWMColor.orange1].hexStrings)
		}
	}
	
	static var previews: some View {
		Group {
			SegmentedRadioPickerManager<SegmentedButton>()
		}
		.preferredColorScheme(.dark)
	}
}
