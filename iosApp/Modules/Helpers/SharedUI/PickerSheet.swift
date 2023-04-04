import SwiftUI
import ModuleLinker
import Colors

public struct PickerSheet<Option>: View where Option: CustomStringConvertible & Equatable & Hashable {
	public let options: [Option]
	@Binding public var selectedOption: Option
	public let buttonBackground: [String]
	public let title: String
	public let onSelect: () -> ()
	
	public init(options: [Option], selectedOption: Binding<Option>, buttonBackground: [String], title: String, onSelect: @escaping () -> ()) {
		self.options = options
		self._selectedOption = selectedOption
		self.buttonBackground = buttonBackground
		self.title = title
		self.onSelect = onSelect
	}
	
	public var body: some View {
		VStack {
			NEWMColor.grey400.swiftUIColor.frame(height: 2)
			ScrollView(showsIndicators: false) {
				HStack {
					Text(title).font(.interMedium(ofSize: 12)).foregroundColor(NEWMColor.grey100())
					Spacer()
				}
				.addSidePadding()
				.padding(.bottom, 20)
				.padding([.top], 30)
				VStack(alignment: .center, spacing: 16) {
					ForEach(options, id: \.self, content: pickerButton)
				}
				.addSidePadding()
			}
		}
	}
	
	@ViewBuilder
	private func pickerButton(_ option: Option) -> some View {
		HStack {
			Spacer()
			Text(option.description)
			Spacer()
		}
		.padding()
		.background(selectedOption == option ? buttonBackground.gradient.erased : Color.clear.erased)
		.cornerRadius(4)
		.borderOverlay(color: selectedOption == option ? Color.clear : buttonBackground.gradient, radius: 4, width: 2)
		.onTapGesture {
			selectedOption = option
			onSelect()
		}
		.font(.interMedium(ofSize: 16))
		.foregroundStyle(selectedOption == option ? ["#ffffff"].gradient : buttonBackground.gradient)
	}
}

struct PickerSheet_Previews: PreviewProvider {
	static let options = ["option 1", "option 2", "option 3"]

	struct PickerContainer: View {
		@State var showSheet = true
		@State var currentSelection: String = options.first!

		var body: some View {
			Button(currentSelection) {
				showSheet = true
			}
			.sheet(isPresented: $showSheet) {
				PickerSheet(options: options, selectedOption: $currentSelection, buttonBackground: ["#a32143", "#c9213f"], title: "SELECT MAIN CURRENCY", onSelect: { showSheet = false })
			}
		}
	}
    static var previews: some View {
		Group {
			PickerContainer()
			PickerSheet(options: options, selectedOption: .constant(options.first!), buttonBackground: ["#00FFFF", "#000FFF"], title: "SELECT MAIN CURRENCY", onSelect: { })
		}
		.preferredColorScheme(.dark)
    }
}
