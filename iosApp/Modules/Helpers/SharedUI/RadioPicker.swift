import Foundation
import SwiftUI

public protocol RadioButtonProtocol: View {
	associatedtype Option
	init(_ option: Option, selectedOption: Binding<Option?>)
}

public typealias RadioOption = CustomStringConvertible & Equatable & Hashable

public struct RadioPicker<Option, RadioButtonType: RadioButtonProtocol>: View where Option: RadioOption, Option == RadioButtonType.Option {
	private let options: [Option]
	@Binding public var selectedOption: Option
	
	private let RadioButtonType: any RadioButtonProtocol.Type
	
	public init(options: [Option], selectedOption: Binding<Option>, RadioButtonType: RadioButtonType.Type) {
		self.options = options
		self._selectedOption = selectedOption
		self.RadioButtonType = RadioButtonType
	}
	
	public var body: some View {
		ScrollView(.horizontal, showsIndicators: false) {
			HStack(alignment: .center, spacing: 16) {
				ForEach(options, id: \.self) { option in
					RadioButtonType(option, selectedOption: Binding<Option?>($selectedOption))
				}
			}
			.padding(.leading, sidePadding)
		}
	}
}
