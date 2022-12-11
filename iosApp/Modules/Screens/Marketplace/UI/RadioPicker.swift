import SwiftUI
import Colors
import Fonts

struct RadioPicker<Option>: View where Option: CustomStringConvertible & Equatable & Hashable {
	let options: [Option]
	@Binding var selectedOption: Option
	
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
				if self.selectedOption == self.option {
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
}

struct RadioPicker_Previews: PreviewProvider {
	struct RadioPickerManager: View {
		@State var option: String = "Option 1"
		
		var body: some View {
			RadioPicker(options: ["Option 1", "Option 2", "Option 3", "Option 4", "Option 5", "Option 6"], selectedOption: $option)
		}
	}
	
	static var previews: some View {
		RadioPickerManager()
			.preferredColorScheme(.dark)
	}
}
