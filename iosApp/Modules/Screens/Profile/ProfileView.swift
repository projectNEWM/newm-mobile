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
import Analytics

public struct ProfileView: View {
	@StateObject private var viewModel = ProfileViewModel()
	private let userImageSize: CGFloat = 128
	private let sectionSpacing: CGFloat = 12
	@State private var showXPubScanner = false
	@State private var showBottomSheet = false

	public init() {}
	
	public var body: some View {
		NavigationView {
			mainView
				.analyticsScreen(name: AppScreens.AccountScreen().name)
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
					optionButtons.padding(.top, -20)
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
		.moreButtonTopRight {
			showBottomSheet = true
		}
		.sheet(isPresented: $showBottomSheet) {
			bottomSheet
		}
	}
}

extension ProfileView {
	@ViewBuilder
	private var optionButtons: some View {
		VStack(alignment: .center, spacing: 16) {
			NavigationLink {
				editView
			} label: {
				buttonText(Text("Edit profile").foregroundStyle(NEWMColor.midMusic()), backgroundGradient: Gradients.mainPrimaryLight)
			}
			
			NavigationLink {
				EmptyView()
					.backButton()
			} label: {
				buttonText(Text("Manage wallets").foregroundStyle(NEWMColor.midMusic()), backgroundGradient: Gradients.mainPrimaryLight)
			}
		}
		.padding()
	}
	
	@ViewBuilder
	private var editView: some View {
		ZStack {
			ScrollView {
				VStack(alignment: .center) {
					if let bannerURL = viewModel.bannerURL {
						HeaderImageSection(bannerURL.absoluteString)
							.padding(.top, 177)
					}
					Group {
						artistImage
						artistName
							.padding(.top, -30)
						bottomSection
							.padding(.top, -30)
						saveButton
					}
					.padding()
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
		.confirmationDialog(
			"Are you sure?",
			isPresented: $viewModel.showDeleteUserConfirmation,
			titleVisibility: .visible
		) {
			Button("Delete", role: .destructive) {
				Task {
					await viewModel.deleteUser()
				}
			}
			Button("Cancel", role: .cancel) {}
		} message: {
			Text("This action cannot be undone.")
		}
		.backButton(withToolbar: true)
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
			actionButton(title: "Save changes", backgroundGradient: Gradients.mainPrimaryLight) {
				Task { await viewModel.save() }
			}
			.disabled(viewModel.enableSaveButon == false)
			.padding()
		}
	}
	
	@ViewBuilder
	private var artistImage: some View {
		if let picUrl = viewModel.pictureURL {
			KFImage(picUrl)
				.setProcessor(DownsamplingImageProcessor(size: CGSize(width: userImageSize, height: userImageSize)))
				.clipShape(RoundedRectangle(cornerRadius: userImageSize/2.0))
				.padding(.top, -(sectionSpacing + (viewModel.bannerURL == nil ? -20 : (userImageSize/2))))
		}
	}
	
	@ViewBuilder
	private var bottomSection: some View {
		VStack(alignment: .leading, spacing: 20) {
			NEWMTextField(title: "EMAIL", prompt: "", isSecure: false, text: .constant(viewModel.email), disabled: true)
				.textContentType(.emailAddress)
				.keyboardType(.asciiCapable)
			Divider()
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
			Task {
				await viewModel.disconnectWallet()
			}
		}
	}
		
	@ViewBuilder
	private func bottomSectionHeader(_ text: String) -> some View {
		Text(text).font(.inter(ofSize: 16).bold())
	}
	
	@ViewBuilder
	private var bottomSheet: some View {
		ZStack {
			Color.black.edgesIgnoringSafeArea(.all)
			
			VStack {
				Spacer()
				
				VStack(spacing: 10) {
					Link(destination: URL(string: "https://newm.io/app-tos")!) {
						Text("Terms & Conditions")
							.frame(maxWidth: .infinity)
							.padding()
							.background(Gradients.mainPrimary.opacity(0.08))
							.foregroundColor(NEWMColor.midMusic.swiftUIColor)
							.cornerRadius(8)
					}
					
					Link(destination: URL(string: "https://newm.io/app-privacy")!) {
						Text("Privacy Policy")
							.frame(maxWidth: .infinity)
							.padding()
							.background(Gradients.mainPrimary.opacity(0.08))
							.foregroundColor(NEWMColor.midMusic.swiftUIColor)
							.cornerRadius(8)
					}
					
					Button(action: {
						viewModel.logOut()
					}) {
						Text("Logout")
							.frame(maxWidth: .infinity)
							.padding()
							.background(try! Color(hex: "#EB5545").opacity(0.08))
							.foregroundColor(NEWMColor.error.swiftUIColor)
							.cornerRadius(8)
					}
					
					Button(action: {
						showBottomSheet = false
						viewModel.showDeleteUserConfirmation = true
					}) {
						Text("Delete Account")
							.frame(maxWidth: .infinity)
							.padding()
							.background(try! Color(hex: "#EB5545").opacity(0.08))
							.foregroundColor(NEWMColor.error.swiftUIColor)
							.cornerRadius(8)
					}
				}
				.padding()
				.background(Color.black.opacity(0.8))
				.cornerRadius(16)
			}
		}
		.presentationDetents([.height(264)])
	}
}

#if DEBUG
#Preview {
	Resolver.root = .mock
	MocksModule.shared.registerAllMockedServices(mockResolver: .mock)
	return ProfileView()
		.preferredColorScheme(.dark)
}
#endif
