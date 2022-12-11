import SwiftUI
import Colors

struct Filters<Option1, Option2>: View where Option1: CustomStringConvertible & Hashable, Option2: CustomStringConvertible & Hashable {
	@Binding var selectedOption1: Option1
	@Binding var selectedOption2: Option2
	
	let allOptions1: [Option1]
	let allOptions2: [Option2]
	
	let middlePrompt: String
	let showNew: Bool

	var body: some View {
		HStack(spacing: 4) {
			//TODO: localize
			if showNew {
				Text("New")
			}
			//This is dumb but the label on the Picker doesn't show up.  https://stackoverflow.com/questions/69381385/swiftui-custom-picker-label-not-rendering
			Menu {
				Picker("", selection: $selectedOption1) {
					ForEach(allOptions1, id: \.description) {
						Text($0.description).tag($0)
					}
				}
			} label: {
				pickerLabel(selectedOption1.description)
			}
			Text(" \(middlePrompt) ")
			//This is dumb but the label on the Picker doesn't show up.  https://stackoverflow.com/questions/69381385/swiftui-custom-picker-label-not-rendering
			Menu {
				Picker("", selection: $selectedOption2) {
					ForEach(allOptions2, id: \.hashValue) {
						Text($0.description).tag($0)
					}
				}
			} label: {
				pickerLabel(selectedOption2.description)
			}
		}
		.font(Font.interMedium(ofSize: 14))
		.lineLimit(1)
		.minimumScaleFactor(0.5)
		.foregroundColor(.white)
	}
	
	@ViewBuilder
	private func pickerLabel(_ text: String) -> some View {
		Text(text)
			.padding(10)
			.background(NEWMColor.grey600())
			.cornerRadius(500)
			.overlay(RoundedRectangle(cornerRadius: 500)
				.stroke(NEWMColor.grey500(), lineWidth: 2))
	}
}

struct Filters_Previews: PreviewProvider {
	static var previews: some View {
		Group {
			Filters(selectedOption1: .constant("Option1"),
					selectedOption2: .constant("Option2"),
					allOptions1: ["Option1"],
					allOptions2: ["Option2"],
					middlePrompt: "most popular",
					showNew: false)
			
			Filters(selectedOption1: .constant("Option1"),
					selectedOption2: .constant("Option2"),
					allOptions1: ["Option1"],
					allOptions2: ["Option2"],
					middlePrompt: "most popular",
					showNew: true)
		}
		.preferredColorScheme(.dark)
	}
}
