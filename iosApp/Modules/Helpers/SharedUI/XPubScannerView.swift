import SwiftUI
import shared
import Resolver
import ModuleLinker

public struct XPubScannerView: View {
	private let completion: () -> ()
	@State private var manuallyEnteredXPub: String = ""
	@Injected private var connectWalletXPubUseCase: any ConnectWalletUseCase
	@State private var error: Error?
	
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
					case .success(let xPub):
						success(xPub: xPub)
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
				TextField("", text: $manuallyEnteredXPub, prompt: Text("Enter xPub key"))
					.padding(.leading, 12)
					.padding(.trailing, 5)
					.padding(.vertical, 12)
					.frame(maxWidth: .infinity, minHeight: 40, maxHeight: 40, alignment: .center)
					.background(Color(red: 0.14, green: 0.14, blue: 0.14))
					.cornerRadius(8)
				
				let disabled = manuallyEnteredXPub.isEmpty
				Button {
					success(xPub: manuallyEnteredXPub)
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
	
	private func success(xPub: String) {
		connectWalletXPubUseCase.connect(xpub: xPub)
		completion()
	}
}

#Preview {
	XPubScannerView { }
		.preferredColorScheme(.dark)
}
