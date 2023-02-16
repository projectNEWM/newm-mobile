import SwiftUI
import Colors

struct LoginTextField: View {
	let title: String?
	let prompt: String
	let isSecure: Bool
	@Binding var text: String
	
	var body: some View {
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
			}
		}
		.padding(10)
		.borderOverlay(color: NEWMColor.grey400(), width: 2)
		.font(.inter(ofSize: 16))
		.background(NEWMColor.grey600())
	}
}

struct LoginTextField_Previews: PreviewProvider {
	static var previews: some View {
		Group {
			LoginTextField(title: "Title", prompt: "Prompt", isSecure: true, text: .constant("Text"))
				.previewDisplayName("Secure")
			LoginTextField(title: "Title", prompt: "Prompt", isSecure: false, text: .constant("Text"))
		}
		.preferredColorScheme(.dark)
	}
}
