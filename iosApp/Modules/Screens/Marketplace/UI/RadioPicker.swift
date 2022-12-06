import SwiftUI
import Colors
import Fonts

struct RadioPicker<Option>: View where Option: CustomStringConvertible & Equatable & Hashable {
	let options: [Option]
	@State private var selectedOption: Option
	
	init(options: [Option]) {
		guard options.isEmpty == false else { fatalError("Need some options") }
		
		self.options = options
		_selectedOption = State<Option>(initialValue: options.first!)
	}
	
	var body: some View {
		ScrollView(.horizontal, showsIndicators: false) {
			HStack(alignment: .center, spacing: 16) {
				ForEach(options, id: \.self) { option in
					RadioButton(option: option, selectedOption: Binding<Option?>($selectedOption))
				}
			}
			.padding(.leading, sidePadding)
		}
	}
}

extension RadioPicker {
	struct RadioButton: View {
		let option: Option
		@Binding var selectedOption: Option?
		
		var body: some View {
			Button(action: {
				self.selectedOption = self.option
			}) {
				if selectedOption == option {
					HStack(alignment: .center) {
						Text(option.description)
						Spacer()
					}
					.padding()
					.foregroundColor(.white)
					.frame(height: 36)
					.background(
						LinearGradient(gradient: Gradient(colors: [NEWMColor.orange2, NEWMColor.orange1].map(\.swiftUIColor)),
									   startPoint: .topLeading,
									   endPoint: .bottomTrailing)
					)
				} else {
					HStack(alignment: .center) {
						Text(option.description)
						Spacer()
					}
					.padding()
					.foregroundColor(.white)
					.frame(height: 36)
					.overlay(
						RoundedRectangle(cornerRadius: 4)
							.stroke(NEWMColor.grey500.swiftUIColor, lineWidth: 2)
					)
					.background(NEWMColor.grey600.swiftUIColor)
				}
			}
			.cornerRadius(4)
			.font(.interMedium(ofSize: 14))
		}
	}
}

struct RadioPicker_Previews: PreviewProvider {
	static var previews: some View {
		RadioPicker(options: ["Option 1", "Option 2", "Option 3", "Option 4", "Option 5", "Option 6"])
			.preferredColorScheme(.dark)
	}
}
