import SwiftUI
import Foundation
import Fonts
import Colors

struct SupportButtonsModel: View {
	private let model: SupportButtonsCellModel
	
	init(_ model: SupportButtonsCellModel) {
		self.model = model
	}
	
	var body: some View {
		HStack(alignment: .center) {
			Button {
				print("\(title)")
			} label: {
				Spacer()
				icon
				title
				Spacer()
			}
			.frame(width: 115, height: 35)
			.background(background)
			.border(model.title == "Follow" ? Color.gray.opacity(0.0) : Color.gray, width: 2)
			.cornerRadius(8)
			.foregroundColor(.white)
			.padding(10)
			.fixedSize()
		}
	}
	
	private var title: some View {
		Text(model.title)
			.font(.inter(ofSize: 15))
			.frame(alignment: .center)
			.padding(.trailing, 10)
			.fixedSize()
	}
	
	private var icon: some View {
		model.icon.image
			.resizable()
			.frame(width: 35, height: 35)
	}
	
	private var background: some View {
		model.title == "Follow" ?
		LinearGradient(colors: [NEWMColor.purple.swiftUIColor, NEWMColor.pink.swiftUIColor], startPoint: .bottomLeading, endPoint: .topTrailing) :
		LinearGradient(colors: [.black, .black], startPoint: .bottomLeading, endPoint: .topTrailing)
	}
}

