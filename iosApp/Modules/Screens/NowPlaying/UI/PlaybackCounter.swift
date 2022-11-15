import SwiftUI
import Utilities
import SharedUI
import Colors

struct PlaybackCounter: View {
	let currentTime: String
	let totalTime: String
	let percentComplete: CGFloat
	let artistImageUrl: String
	
	private let timePadding: CGFloat = 30
	
	var body: some View {
		GeometryReader { geometry in
			let padding: CGFloat = 5/6
			let size = geometry.size.width * padding
			HStack(alignment: .center) {
				currentTimeText.padding(.trailing, -15)
				ZStack {
					artistImage(size: size)
						.scaleEffect(x: padding, y: padding, anchor: UnitPoint(x: 0.5, y: 0.5))
					track(filled: false, size: size)
					track(filled: true, size: size)
				}
				totalTimeText.padding(.leading, -15)
			}
		}
	}
	
	private var currentTimeText: some View {
		playbackText(time: currentTime).foregroundColor(.gray).padding(.bottom, timePadding)
	}
	
	private var totalTimeText: some View {
		playbackText(time: totalTime).padding(.bottom, timePadding)
	}
	
//	private var trackThumb: some View {
//		Circle()
//			.position(trackPath(filled: false).currentPoint ?? trackPath(filled: true).currentPoint ?? .zero)
//			.transformEffect(.identity.translatedBy(x: -size/2.0, y: -size/2.0))
////			.scaleEffect(x: 1.2, y: 1.2, anchor: UnitPoint(x: 0.5, y: 0.5))
//			.frame(width: 16, height: 16)
//	}
	
	private func trackPath(filled: Bool, size: CGFloat) -> Path {
		Path(ellipseIn: .init(x: 0, y: 0, width: size, height: size))
			.trimmedPath(from: filled ? 0.0 : percentComplete/2.0, to: filled ? percentComplete/2.0 : 0.5)
			.strokedPath(StrokeStyle(lineWidth: 5))
			.applying(.identity.scaledBy(x: -1, y: 1).translatedBy(x: -size, y: 0))
	}
	
	private func track(filled: Bool, size: CGFloat) -> some View {
		trackPath(filled: filled, size: size)
			.frame(width: size, height: size)
			.foregroundStyle(trackColor(filled: filled))
	}
	
	private func trackColor(filled: Bool) -> AnyShapeStyle {
		filled
		? AnyShapeStyle(LinearGradient(colors: [NEWMColor.pink2.swiftUIColor,
												NEWMColor.purple2.swiftUIColor],
									   startPoint: .leading,
									   endPoint: .trailing))
		: AnyShapeStyle(.gray)
	}
	
	private func playbackText(time: String) -> some View {
		Text(time)
			.font(.caption2)
			.monospacedDigit()
			.lineLimit(1)
	}
	
	private func artistImage(size: CGFloat) -> some View {
		AsyncImage(url: URL(string: artistImageUrl)) { phase in
			switch phase {
			case .empty:
				Image.placeholder.circleImage(size: size)
			case .failure(let error):
				//TODO: log error
				Text(error.localizedDescription)
			case .success(let image):
				image.circleImage(size: size)
			@unknown default:
				Text("Unknown Error")
			}
		}
	}
}

struct PlaybackCounter_Previews: PreviewProvider {
	class PreviewViewModel: ObservableObject {
		let totalTime: Int = 5

		@Published var currentTime: Int = 0
		private var timer: Timer? = nil
		
		init() {
			timer = Timer.scheduledTimer(withTimeInterval: 1.0, repeats: true, block: { [weak self] _ in
				guard let self = self else { return }
				guard self.currentTime >= 0, self.totalTime > self.currentTime else { return }
				self.currentTime += 1
			})
		}
	}
	
	struct PreviewView: View {
		@StateObject var viewModel = PreviewViewModel()
		
		var body: some View {
			PlaybackCounter(currentTime: viewModel.currentTime.playbackTimeString,
							totalTime: viewModel.totalTime.playbackTimeString,
							percentComplete: CGFloat(viewModel.currentTime) / CGFloat(viewModel.totalTime == 0 ? 1 : viewModel.totalTime),
							artistImageUrl: MockData.artists.first!.image)
			.padding(40)
		}
	}
	
	static var previews: some View {
		PreviewView()
			.preferredColorScheme(.dark)
	}
}
