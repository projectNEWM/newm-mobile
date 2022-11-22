import Foundation
import SwiftUI

public struct HorizontalStackSection<Model, Content>: View where Model: Identifiable, Content: View {
	private let hStackSpacing: CGFloat = 12
	let title: String
	let models: [Model]
	@ViewBuilder let content: (Model) -> Content
	
	public init(title: String, models: [Model], @ViewBuilder content: @escaping (Model) -> Content) {
		self.title = title
		self.models = models
		self.content = content
	}
	
	public var body: some View {
		HorizontalScroller(title: title) {
			LazyHStack(spacing: hStackSpacing) {
				ForEach(models, id: \.id) { model in
					content(model)
				}
			}
		}
	}
}
