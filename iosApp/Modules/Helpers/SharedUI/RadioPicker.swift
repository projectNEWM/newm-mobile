import Foundation
import SwiftUI
import Colors

public protocol RadioButtonProtocol<Option>: View where Option: RadioOption {
	associatedtype Option
	init(_ option: Option, selectedOption: Binding<Option?>, gradient: [String])
}

public typealias RadioOption = CustomStringConvertible & Equatable & Hashable

public struct RadioPicker<Option, RadioButtonType: RadioButtonProtocol>: View where Option: RadioOption, Option == RadioButtonType.Option {
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
		ScrollView(.horizontal, showsIndicators: false) {
			HStack(alignment: .center, spacing: 8) {
				ForEach(options, id: \.self) { option in
					RadioButtonType(option, selectedOption: Binding<Option?>($selectedOption), gradient: selectedOption == option ? gradient : [NEWMColor.grey400.hexString])
				}
			}
			.padding(.leading, sidePadding)
		}
	}
}

struct RadioPicker_Previews: PreviewProvider {
	static let options = ["Option 1", "Option 2", "Option 3", "Option 4", "Option 5", "Option 6"]
	struct RadioPickerManager<RadioButtonType: RadioButtonProtocol>: View where RadioButtonType.Option == String {
		@State var option: String = "Option 1"
		
		var body: some View {
			RadioPicker(options: RadioPicker_Previews.options,
						selectedOption: $option,
						RadioButtonType: RadioButtonType.self,
						gradient: [NEWMColor.orange, NEWMColor.orange1].hexStrings)
		}
	}
	
	static var previews: some View {
		Group {
			RadioPickerManager<RadioButton>()
			RadioPickerManager<SegmentedButton>()
		}
		.preferredColorScheme(.dark)
	}
}
