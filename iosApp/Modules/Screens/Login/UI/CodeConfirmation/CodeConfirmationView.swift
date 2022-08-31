import Foundation
import SwiftUI

struct CodeConfirmationView: View {
	@StateObject var viewModel: CodeConfirmationViewModel
	
	var body: some View {
		Spacer()
		VStack(alignment: .center) {
			TextField("Enter confirmation code", text: $viewModel.code)
			Button("Confirm", action: viewModel.confirmTapped)
		}
		Spacer()
	}
}

struct CodeConfirmation_Previews: PreviewProvider {
	static var previews: some View {
		CodeConfirmationView(viewModel: CodeConfirmationViewModel(.init(password: "", passwordConfirmation: "", email: "")))
	}
}
