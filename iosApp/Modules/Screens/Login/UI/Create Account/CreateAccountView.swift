//import Foundation
//import SwiftUI
//
//struct CreateAccountView: View {
//	@State var viewModel = CreateAccountViewModel()
//	
//	var body: some View {
//		if let error = viewModel.error {
//			Text(error)
//		} else {
//			Spacer()
//			VStack {
//				TextField("email", text: $viewModel.email)
//				TextField("password", text: $viewModel.password)
//				TextField("confirm password", text: $viewModel.confirmPassword)
//			}.padding()
//				.links(Links(route: $viewModel.route))
//			Button("Confirm", action: viewModel.confirmTapped)
//			Spacer()
//		}
//	}
//}
//
//struct CreateAccountView_PreviewProvider: PreviewProvider {
//	static var previews: some View {
//		CreateAccountView()
//			.preferredColorScheme(.dark)
//	}
//}
