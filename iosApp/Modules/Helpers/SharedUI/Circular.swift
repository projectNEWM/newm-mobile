import SwiftUI
import ModuleLinker

public struct Circular<Items: View>: View {
	private let items: [AnyView]
	private let radius: Double = 100
	
	public init<D : RandomAccessCollection, I : Hashable, C: View>(@ViewBuilder content: () -> Items) where Items == ForEach<D, I, C> {
		let fe = content() as ForEach<D, I, C>
		items = fe.data.map { AnyView(fe.content($0.self))}
	}
	
	public var body: some View {
		return ZStack {
			ForEach(0..<items.count, id: \.self) { idx in
				self.items[idx].modifier(self.positionModifier(at: idx))
			}
		}
		.frame(width: (2 * CGFloat(radius)), height: (2 * CGFloat(radius)))
	}
	
	func positionModifier(at index: Int) -> _PositionLayout {
		let r = (2 * Double.pi / Double(items.count)) * Double(index)
		return _PositionLayout(position: CGPoint(x: sin(r) * radius + radius, y: cos(r) * radius + radius))
	}
}

struct Circular_Previews: PreviewProvider {
	static var previews: some View {
		Circular {
			ForEach(0..<max(0, 6), id: \.self) { i in
				Color.green
					.frame(width: 80, height: 80)
					.clipShape(Circle())
					.overlay(Text("\(i)").font(.headline).foregroundColor(.white).allowsTightening(true))
					.shadow(radius: 3)
			}
		}
	}
}
