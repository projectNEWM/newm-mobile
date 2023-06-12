import SwiftUI
import Colors

public struct NEWMTextField: View {
	public let title: String?
	public let prompt: String
	public let isSecure: Bool
	public let disabled: Bool
	@Binding var text: String
	
	public init(title: String? = nil, prompt: String, isSecure: Bool, text: Binding<String>, disabled: Bool = false) {
		self.title = title
		self.prompt = prompt
		self.isSecure = isSecure
		self.disabled = disabled
		_text = text
	}
	
	public var body: some View {
		VStack(alignment: .leading, spacing: 6) {
			titleView
			textField
		}
	}
	
	@ViewBuilder
	private var titleView: some View {
		title.flatMap(Text.init)
			.font(.inter(ofSize: 12).weight(.semibold))
			.foregroundColor(NEWMColor.grey100())
	}
	
	@ViewBuilder
	private var textField: some View {
		Group {
			if isSecure {
				SecureField(title ?? "", text: $text, prompt: Text(verbatim: prompt))
			} else {
				TextField(text: $text, prompt: Text(verbatim: prompt), label: { Text(verbatim: title ?? "") })
					.foregroundColor(disabled ? NEWMColor.grey100() : .white)
			}
		}
		.padding(10)
		.borderOverlay(color: disabled ? NEWMColor.grey600() : NEWMColor.grey400(), radius: 4, width: 2)
		.font(.inter(ofSize: 16))
		.background(disabled ? NEWMColor.grey700() : NEWMColor.grey600())
		.disabled(disabled)
	}
}

struct LoginTextField_Previews: PreviewProvider {
	static var previews: some View {
		Group {
			NEWMTextField(title: "Title", prompt: "Prompt", isSecure: true, text: .constant("Text"))
				.previewDisplayName("Secure")
			NEWMTextField(title: "Title", prompt: "Prompt", isSecure: false, text: .constant("Text"))
			NEWMTextField(title: "Title", prompt: "Prompt", isSecure: false, text: .constant("Text"), disabled: true)
		}
		.preferredColorScheme(.dark)
	}
}
