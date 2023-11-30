//import SwiftUI
//import Fonts
//import Colors
//
//struct ProfileMoreView: View {
//	@StateObject private var viewModel = ProfileMoreViewModel()
//	
//	var body: some View {
//		VStack {
//			Divider()
//			VStack(alignment: .leading) {
//				ForEach(viewModel.sections, id: \.title) { section in
//					sectionTitle(section.title)
//					VStack {
//						ForEach(section.rows, id: \.title) { row in
//							Link(destination: URL(string: row.url)!) {
//								Text(row.title)
//									.formatButton(textColor: .white, borderColor: NEWMColor.grey500())
//							}
//						}
//					}
//					.padding(.bottom)
//				}
//				
//				logOutButton
//			}
//			.padding()
//		}
//		.background(.black)
//	}
//	
//	@ViewBuilder
//	private func sectionTitle(_ text: String) -> some View {
//		Text(text)
//			.font(.inter(ofSize: 12))
//			.bold()
//			.foregroundColor(NEWMColor.grey100())
//	}
//
//	@ViewBuilder
//	private var logOutButton: some View {
//		Button {
//			viewModel.logOut()
//		} label: {
//			Text(verbatim: .logOut)
//				.formatButton(textColor: try! Color(hex: "EB5545"), borderColor: try! Color(hex: "EB5545"))
//		}
//	}
//}
//
//private extension View {
//	func formatButton(textColor: Color, borderColor: Color) -> some View {
//		self
//			.font(.inter(ofSize: 14))
//			.bold()
//			.padding()
//			.foregroundColor(textColor)
//			.frame(maxWidth: .infinity)
//			.borderOverlay(color: borderColor, radius: 4, width: 2)
//	}
//}
//
//struct ProfileMoreView_Previews: PreviewProvider {
//	static var previews: some View {
//		ProfileMoreView()
//			.preferredColorScheme(.dark)
//	}
//}
