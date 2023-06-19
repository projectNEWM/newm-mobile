import SwiftUI
import SharedUI
import Models
import Domain
import ModuleLinker
import Resolver
import SharedUI
import Colors

struct ProfileView: View {
	@StateObject private var viewModel = ProfileViewModel()
	private let userImageSize: CGFloat = 128
	private let sectionSpacing: CGFloat = 12
	@State private var keyboardObserver = KeyboardObserver()
	@State private var showMore = false
	
	var body: some View {
		mainView
			.navigationBarItems(trailing: saveButton)
			.autocorrectionDisabled(true)
			.scrollDismissesKeyboard(.immediately)
	}
	
	@ViewBuilder
	private var saveButton: some View {
		if viewModel.showSaveButton {
			Button("Save", action: viewModel.save)
				.foregroundStyle(Gradients.loginGradient.gradient)
				.erased
		} else {
			Button {
				showMore = true
			} label: {
				Image(systemName: "ellipsis")
					.rotationEffect(.degrees(90))
					.foregroundColor(.white)
			}
		}
	}
	
//	@ViewBuilder
//	private var saveButton: some View {
//		VStack {
//			Spacer()
//			if viewModel.showSaveButton {
//				actionButton(title: "Save", action: viewModel.save).addSidePadding()
//			}
//		}
//	}
	
	@ViewBuilder
	private var mainView: some View {
		ZStack {
			ScrollView {
				VStack(alignment: .leading) {
					HeaderImageSection(viewModel.user?.bannerUrl?.absoluteString)
					artistImage
					bottomSection
						.addSidePadding()
				}
				.padding(.top, 177)
			}
			.alert("Error", isPresented: isPresent($viewModel.error), actions: {}) {
				Text(viewModel.error ?? "Unknown error")
			}
			.refreshable {
				await viewModel.loadUser()
			}
			.loadingToast(isLoading: $viewModel.isLoading)
		}
		.sheet(isPresented: $showMore) {
			ProfileMoreView()
				.presentationDetents([.medium])
		}
	}
	
	@ViewBuilder
	private var artistImage: some View {
		AsyncImage(url: viewModel.user?.pictureUrl)
			.circle(size: userImageSize)
			.padding(.top, -(sectionSpacing+userImageSize/2))
	}
	
	@ViewBuilder
	private var bottomSection: some View {
		VStack(alignment: .leading, spacing: 20) {
			bottomSectionHeader("YOUR PROFILE").padding(.top, 40)
			NEWMTextField(title: "FIRST NAME", prompt: "", isSecure: false, text: $viewModel.firstName)
				.textContentType(.givenName)
				.keyboardType(.asciiCapable)
			NEWMTextField(title: "LAST NAME", prompt: "", isSecure: false, text: $viewModel.lastName)
				.textContentType(.familyName)
				.keyboardType(.asciiCapable)
			NEWMTextField(title: "EMAIL", prompt: "", isSecure: false, text: .constant(viewModel.email), disabled: true).padding(.bottom)
				.textContentType(.emailAddress)
				.keyboardType(.asciiCapable)
			Divider().padding(.bottom)
			bottomSectionHeader("CHANGE PASSWORD")
			NEWMTextField(title: "CURRENT PASSWORD", prompt: "Your password", isSecure: true, text: $viewModel.currentPassword)
				.textContentType(.password)
			NEWMTextField(title: "NEW PASSWORD", prompt: "New password", isSecure: true, text: $viewModel.newPassword)
				.textContentType(.newPassword)
			NEWMTextField(title: "CONFIRM NEW PASSWORD", prompt: "New password", isSecure: true, text: $viewModel.confirmPassword).padding(.bottom)
				.textContentType(.newPassword)
		}
	}
	
	private func bottomSectionHeader(_ text: String) -> some View {
		Text(text).font(.inter(ofSize: 16).bold())
	}
}

struct ProfileView_Previews: PreviewProvider {
	static var previews: some View {
		Resolver.root = .mock
		return NavigationView {
			NavigationLink(destination: ProfileView().backButton(withToolbar: true), isActive: .constant(true), label: { EmptyView() })
		}
		.preferredColorScheme(.dark)
	}
}
