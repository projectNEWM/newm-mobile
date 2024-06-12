import SwiftUI
import shared
import Resolver
import ModuleLinker
import Utilities

@MainActor
public struct ConnectWalletToAccountScannerView: View {
	private let completion: () -> ()
	@State private var manuallyEnteredCode: String = ""
	@Injected private var connectWalletToAccountUseCase: any ConnectWalletUseCase
	@State private var isLoading = false
	@State private var error: Error? {
		didSet {
			error.flatMap(Resolver.resolve(ErrorReporting.self).logError)
		}
	}
	
	public init(completion: @escaping () -> Void) {
		self.completion = completion
	}
	
	public var body: some View {
		VStack {
			HStack {
				title.padding()
				Spacer()
			}
			VStack {
				QRCodeScannerView { result in
					switch result {
					case .success(let code):
						success(id: code)
					case .failure(let error):
						self.error = error
					}
				}
				.frame(width: 346, height: 337)
				.clipShape(RoundedRectangle(cornerRadius: 32))
				aimSubtitle
			}
			Spacer()
			manualEntry
		}
		.alert("Error", isPresented: isPresent($error)) {
			Button {
				error = nil
			} label: {
				Text("Ok")
			}
		}
		.loadingToast(shouldShow: $isLoading)
	}
	
	@ViewBuilder
	private var title: some View {
		Text("Connect Wallet")
			.font(
				Font.custom("Inter", size: 24)
					.weight(.bold)
			)
			.foregroundColor(Color(red: 0.25, green: 0.75, blue: 0.57))
	}
	
	@ViewBuilder
	private var aimSubtitle: some View {
		Text("Aim at the wallet QRCode")
			.font(Font.custom("Inter", size: 12))
			.multilineTextAlignment(.center)
			.foregroundColor(Color(red: 0.56, green: 0.56, blue: 0.57))
	}
	
	@ViewBuilder
	private var manualEntry: some View {
		VStack(alignment: .leading, spacing: 4) {
			Text("OR USE THE XPUB KEY")
				.font(
					Font.custom("Inter", size: 12)
						.weight(.bold)
				)
				.foregroundColor(Color(red: 0.44, green: 0.44, blue: 0.44))
			
			HStack {
				TextField("", text: $manuallyEnteredCode, prompt: Text("Enter xPub key"))
					.padding(.leading, 12)
					.padding(.trailing, 5)
					.padding(.vertical, 12)
					.frame(maxWidth: .infinity, minHeight: 40, maxHeight: 40, alignment: .center)
					.background(Color(red: 0.14, green: 0.14, blue: 0.14))
					.cornerRadius(8)
				
				let disabled = manuallyEnteredCode.isEmpty
				Button {
					success(id: manuallyEnteredCode)
				} label: {
					Text("Connect")
						.foregroundColor(
							disabled ?
								.gray :
								Color(red: 0.25, green: 0.75, blue: 0.57)
						)
				}
				.disabled(disabled)
			}
		}
		.padding(0)
		.frame(width: 358, alignment: .topLeading)
	}
	
	private func success(id: String) {
		isLoading = true
		Task {
			defer { isLoading = false }
			do {
				try await connectWalletToAccountUseCase.connect(walletConnectionId: id)
				completion()
			} catch {
				self.error = error
			}
		}
	}
}

#Preview {
	ConnectWalletToAccountScannerView { }
		.preferredColorScheme(.dark)
}
