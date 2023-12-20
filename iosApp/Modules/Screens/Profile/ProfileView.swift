import SwiftUI
import SharedUI
import ModuleLinker
import Resolver
import Colors
import Kingfisher
import shared
import Combine

public struct ProfileView: View {
	@StateObject private var viewModel = ProfileViewModel()
	private let userImageSize: CGFloat = 128
	private let sectionSpacing: CGFloat = 12
	@State private var showMore = false
	@State private var showXPubScanner = false
	
	public init() {}
	
	public var body: some View {
		mainView
			.navigationBarItems(trailing: saveButton)
			.autocorrectionDisabled(true)
			.scrollDismissesKeyboard(.immediately)
	}
	
	@ViewBuilder
	private var mainView: some View {
		ZStack {
			ScrollView {
				VStack(alignment: .center) {
					HeaderImageSection(viewModel.user?.bannerUrl)
					artistImage
					artistName
					walletView.padding(.bottom)
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
		.sheet(isPresented: $showXPubScanner) {
			XPubScannerView {
				showXPubScanner = false
			}
		}
	}
	
	@ViewBuilder
	private var artistName: some View {
		VStack {
			Text(viewModel.fullName)
				.font(
					Font.custom("Inter", size: 24)
						.weight(.bold)
				)
				.multilineTextAlignment(.center)
				.foregroundColor(.white)
			
			Text(viewModel.nickname)
			  .font(Font.custom("Inter", size: 14))
			  .multilineTextAlignment(.center)
			  .foregroundColor(Color(red: 0.56, green: 0.56, blue: 0.58))
		}
		.padding(.bottom, 30)
		.padding(.top, 10)
	}
	
	@ViewBuilder
	private var saveButton: some View {
		if viewModel.showSaveButton {
			Button("Save", action: viewModel.save)
				.foregroundStyle(Gradients.loginGradient.gradient)
				.erased
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
	private var artistImage: some View {
		KFImage(viewModel.user?.pictureUrl.flatMap(URL.init))
			.setProcessor(DownsamplingImageProcessor(size: CGSize(width: userImageSize, height: userImageSize)))
			.clipShape(RoundedRectangle(cornerRadius: userImageSize/2.0))
			.padding(.top, -(sectionSpacing+userImageSize/2))
	}
	
	@ViewBuilder
	private var bottomSection: some View {
		VStack(alignment: .leading, spacing: 20) {
			NEWMTextField(title: "NICKNAME", prompt: "", isSecure: false, text: .constant(viewModel.nickname), disabled: true)
				.textContentType(.name)
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
	
	@ViewBuilder
	private var connectWalletView: some View {
		ConnectWalletAlertView {
			showXPubScanner = true
		}
	}
	
	@ViewBuilder
	private var disconnectWalletView: some View {
		DisconnectWalletAlertView {
			viewModel.disconnectWallet()
		}
	}
	
	@ViewBuilder
	private var walletView: some View {
		if viewModel.isWalletConnected {
			disconnectWalletView
		} else {
			connectWalletView
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
