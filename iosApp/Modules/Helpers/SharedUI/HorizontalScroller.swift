import SwiftUI

public struct HorizontalScroller<Content>: View where Content: View {
	public let title: String
	@ViewBuilder public let content: () -> Content
	
	public init(title: String, @ViewBuilder content: @escaping () -> Content) {
		self.title = title
		self.content = content
	}
	
	public var body: some View {
		ScrollView(.horizontal, showsIndicators: false) {
			content()
				.addSidePadding()
				.fixedSize()
				.erased
		}
		.addSectionTitle(title)
	}
}
