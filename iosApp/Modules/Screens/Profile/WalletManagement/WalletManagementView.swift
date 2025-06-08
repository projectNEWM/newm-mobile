import SwiftUI
import Kingfisher
import Colors
import SharedUI
import shared

struct WalletManagementView: View {
	@StateObject private var viewModel = WalletManagementViewModel()
	@State private var showConnectWalletSheet: Bool = false
	@State private var showCopiedAlert: Bool = false
	@State private var showWalletPopover: WalletConnection?

	var body: some View {
		NavigationView {
			VStack {
				connectionList
				if showCopiedAlert {
					copiedAlert
				}
				connectWalletButton
			}
			.padding(.horizontal, 16)
		}
		.navigationBarTitleDisplayMode(.inline)
		.refreshable {
			await viewModel.refresh(isPullToRefresh: true)
		}
		.loadingToast(shouldShow: $viewModel.isLoading)
		.sheet(isPresented: $showConnectWalletSheet) {
			ConnectWalletToAccountScannerView {
				Task {
					showConnectWalletSheet = false
					await viewModel.refresh()
				}
			}
		}
		.toolbar {
			Group {
				ToolbarItem(placement: .topBarLeading) {
					BackButton()
				}
				ToolbarItem(placement: .topBarLeading) {
					Text("Connected Wallets")
						.font(.newmTitle1)
						.foregroundStyle(Gradients.libraryGradient.gradient)
				}
			}
		}
		.navigationBarBackButtonHidden(true)
	}
	
	@ViewBuilder
	private var connectionList: some View {
		ScrollView {
			VStack(alignment: .leading, spacing: 12) {
				ForEach(viewModel.walletConnections) { walletConnection in
					HStack(spacing: 12) {
						Image.placeholder
							.resizable()
							.frame(width: 40, height: 40)
							.clipShape(Circle())
							.padding(.vertical, 8)
						
						VStack(alignment: .leading) {
							Text(walletConnection.id)
								.font(.inter(ofSize: 14).weight(.medium))
								.lineLimit(1)
							
							Text(walletConnection.stakeAddress.shortened)
								.font(.inter(ofSize: 12))
								.foregroundStyle(NEWMColor.grey100())
						}
						
						Spacer()
						
						Button {
							showWalletPopover = walletConnection
						} label: {
							HStack(alignment: .center, spacing: 8) {
								Image(systemName: "ellipsis").rotationEffect(.degrees(90))
							}
							.padding(.vertical, 10)
							.frame(width: 40, height: 40, alignment: .center)
							.background(Color(red: 0.09, green: 0.09, blue: 0.09))
							.cornerRadius(8)
							.tint(.white)
							.popover(item: $showWalletPopover) { walletConnection in
								popover(for: walletConnection)
							}
						}
					}
				}
			}
		}
		.padding(.vertical)
	}
	
	@ViewBuilder
	private var connectWalletButton: some View {
		actionButton(
			title: Text("+  Connect wallet").foregroundStyle(NEWMColor.midMusic()),
			backgroundGradient: Gradients.mainPrimaryLight
		) {
			showConnectWalletSheet = true
		}
	}
	
	@ViewBuilder
	private func popover(for walletConnection: WalletConnection) -> some View {
		VStack(spacing: 0) {
			Button {
				UIPasteboard.general.string = walletConnection.stakeAddress
				showWalletPopover = nil
				showCopiedAlert = true
			} label: {
				HStack {
					Asset.Media.fileCopyFill()
					Text("Copy address")
				}
				.padding()
			}
			
			Divider()
			
			Button {
				Task {
					await viewModel.disconnectWallet(walletConnection)
				}
			} label: {
				HStack {
					Asset.Media.closeFill()
					Text("Disconnect")
				}
				.padding()
			}
		}
		.font(.inter(ofSize: 12))
		.foregroundStyle(.white)
		.presentationCompactAdaptation(.popover)
	}
	
	@ViewBuilder
	private var copiedAlert: some View {
		HStack(spacing: 12) {
			Image(systemName: "checkmark")
				.font(.system(size: 12, weight: .bold))
				.foregroundColor(.black)
				.padding(8)
				.background(Circle().fill(Color.green))
			
			Text("Wallet address copied successfully!")
				.minimumScaleFactor(0.5)
				.font(.inter(ofSize: 12))
				.bold()
				.foregroundColor(.white)
				.lineLimit(1)
			
			Spacer(minLength: 0)
			
			Button {
				showCopiedAlert = false
			} label: {
				Image(systemName: "xmark")
					.font(.system(size: 13, weight: .bold))
			}
			.buttonStyle(.plain)
			.foregroundColor(.white)
		}
		.padding(.vertical, 12)
		.padding(.horizontal, 16)
		.background(
			RoundedRectangle(cornerRadius: 8, style: .continuous)
				.fill(Color.black)
		)
		.overlay(
			RoundedRectangle(cornerRadius: 8, style: .continuous)
				.stroke(Color.green, lineWidth: 1)
		)
	}

	private func shortenAddress(_ address: String, prefixLength: Int = 6, suffixLength: Int = 4) -> String {
		guard address.count > prefixLength + suffixLength else { return address }
		let prefix = address.prefix(prefixLength)
		let suffix = address.suffix(suffixLength)
		return "\(prefix)...\(suffix)"
	}
}

extension String {
	var shortened: String {
		let prefix = prefix(7)
		let suffix = suffix(4)
		return "\(prefix)...\(suffix)"
	}
}

extension WalletConnection: Identifiable {}

#if DEBUG
import Mocks
import Resolver

#Preview {
	MocksModule.shared.registerAllMockedServices(mockResolver: .mock)
	ProfileModule.shared.registerAllMockedServices(mockResolver: .mock)
	Resolver.root = .mock
	return WalletManagementView()
		.preferredColorScheme(.dark)
}
#endif
