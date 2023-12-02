import Foundation
import SwiftUI

public struct ConnectWalletAlertView: View {
	let buttonTapped: () -> ()
	
	public init(buttonTapped: @escaping () -> Void) {
		self.buttonTapped = buttonTapped
	}
	
	public var body: some View {
		VStack(alignment: .center, spacing: 16) {
			VStack(alignment: .leading, spacing: 0) {
				Text("You don't have a wallet connected")
					.font(Font.custom("Inter", size: 14))
					.foregroundColor(.white)
				Text("Connect to access all your songs")
					.font(Font.custom("Inter", size: 12))
					.foregroundColor(Color(red: 0.56, green: 0.56, blue: 0.57))
			}
			.padding(0)
			.frame(maxWidth: .infinity, alignment: .topLeading)
			
			Button(action: buttonTapped) {
				Text("Connect wallet")
				  .font(
					Font.custom("Inter", size: 14)
					  .weight(.medium)
				  )
				  .padding(.leading, 14)
				  .padding(.trailing, 16)
				  .padding(.vertical, 0)
				  .frame(maxWidth: .infinity, minHeight: 40, maxHeight: 40, alignment: .center)
				  .foregroundColor(Color(red: 0.27, green: 0.67, blue: 0.75))
				  .background(
					  LinearGradient(
						  stops: [
							  Gradient.Stop(color: Color(red: 0.25, green: 0.75, blue: 0.57).opacity(0.08), location: 0.00),
							  Gradient.Stop(color: Color(red: 0.31, green: 0.57, blue: 0.92).opacity(0.08), location: 1.00),
						  ],
						  startPoint: UnitPoint(x: 0, y: 1),
						  endPoint: UnitPoint(x: 1, y: 0)
					  )
				  )
				  .cornerRadius(8)
			}
		}
		.padding(16)
		.frame(width: 358, alignment: .center)
		.background(Color(red: 0.09, green: 0.09, blue: 0.09))
		.cornerRadius(8)
	}
}

public struct DisconnectWalletAlertView: View {
	let buttonTapped: () -> ()
	
	public init(buttonTapped: @escaping () -> Void) {
		self.buttonTapped = buttonTapped
	}
	
	public var body: some View {
		VStack(alignment: .center, spacing: 16) {
			Button(action: buttonTapped, label: {
				Text("Disconnect wallet")
					.font(
						Font.custom("Inter", size: 14)
							.weight(.medium)
					)
					.padding(.leading, 14)
					.padding(.trailing, 16)
					.padding(.vertical, 0)
					.frame(maxWidth: .infinity, minHeight: 40, maxHeight: 40, alignment: .center)
					.foregroundColor(Color(red: 0.27, green: 0.67, blue: 0.75))
					.background(
						LinearGradient(
							stops: [
								Gradient.Stop(color: Color(red: 0.25, green: 0.75, blue: 0.57).opacity(0.08), location: 0.00),
								Gradient.Stop(color: Color(red: 0.31, green: 0.57, blue: 0.92).opacity(0.08), location: 1.00),
							],
							startPoint: UnitPoint(x: 0, y: 1),
							endPoint: UnitPoint(x: 1, y: 0)
						)
					)
					.cornerRadius(8)
			})
		}
		.padding(16)
		.frame(width: 358, alignment: .center)
		.background(Color(red: 0.09, green: 0.09, blue: 0.09))
		.cornerRadius(8)
	}
}



#Preview {
	ConnectWalletAlertView { }
}
