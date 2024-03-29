import SwiftUI
import Mocks
import SharedUI
import ModuleLinker
import Resolver
import Colors
import Kingfisher
import shared
import Combine
import Utilities

public struct ProfileView: View {
	@StateObject private var viewModel = ProfileViewModel()
	private let userImageSize: CGFloat = 128
	private let sectionSpacing: CGFloat = 12
	@State private var showMore = false
	@State private var showXPubScanner = false
	
	public init() {}
	
	public var body: some View {
		NavigationView {
			mainView
				.toolbar {
					ToolbarItem(placement: .topBarTrailing) {
						saveButton
					}
				}
				.toolbar(.visible, for: .navigationBar)
				.autocorrectionDisabled(true)
				.scrollDismissesKeyboard(.immediately)
		}
	}
}

extension ProfileView {
	@ViewBuilder
	private var mainView: some View {
		ZStack {
			ScrollView {
				VStack(alignment: .center) {
					if let bannerURL = viewModel.bannerURL {
						HeaderImageSection(bannerURL.absoluteString)
							.padding(.top, 177)
					}
					artistImage
					artistName
					walletView.padding(.bottom)
					bottomSection
						.addSidePadding()
				}
			}
			.refreshable {
				await viewModel.loadUser()
			}
			.errorAlert(message: viewModel.errorAlert) {
				viewModel.alertDismissed()
			}
			.toast(shouldShow: $viewModel.showLoadingToast, type: .loading)
			.toast(shouldShow: $viewModel.showCompletionToast, type: .complete)
		}
		.sheet(isPresented: $showXPubScanner, onDismiss: {
			showXPubScanner = false
		}) {
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
			Button("Save", action: { Task { await viewModel.save() } })
				.tint(Gradients.loginGradient.gradient)
				.disabled(viewModel.enableSaveButon == false)
		}
	}
	
	@ViewBuilder
	private var artistImage: some View {
		KFImage(viewModel.pictureURL)
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
			NEWMTextField(title: "CONFIRM NEW PASSWORD", prompt: "New password", isSecure: true, text: $viewModel.confirmPassword)
				.textContentType(.newPassword)
				.padding(.bottom)
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

#if DEBUG
struct ProfileView_Previews: PreviewProvider {
	static var previews: some View {
		MocksModule.shared.registerAllMockedServices()
		Resolver.root = .mock
		return Group {
			NavigationView {
				NavigationLink(destination: ProfileView().backButton(withToolbar: true), isActive: .constant(true), label: { EmptyView() })
			}
		}
		.preferredColorScheme(.dark)
	}
}
#endif

#if DEBUG
struct ProfileView_Previews_2: PreviewProvider {
	static var previews: some View {
		MocksModule.shared.registerAllMockedServices()
		Resolver.root = .mock
		Resolver.mock.register {
			MockUserDetailsUseCase(mockUser: .bannerlessUser) as UserDetailsUseCase
		}
		return Group {
			NavigationView {
				NavigationLink(destination: ProfileView().backButton(withToolbar: true), isActive: .constant(true), label: { EmptyView() })
			}
		}
		.preferredColorScheme(.dark)
		.previewDisplayName("no banner")
	}
}
#endif
