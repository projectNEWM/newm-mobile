import SwiftUI
import Utilities
import SharedUI
import Colors

struct PlaybackCounter: View {
	let currentTime: String
	let totalTime: String
	let percentComplete: CGFloat
	let artistImageUrl: String
	let size: CGFloat

	private let timePadding: CGFloat = 30
	
	var body: some View {
		VStack {
			HStack(alignment: .center) {
				currentTimeText
				ZStack {
					artistImage
					track(filled: false)
					track(filled: true)
//					trackThumb
				}
				totalTimeText
			}
		}
	}
	
	private var currentTimeText: some View {
		playbackText(time: currentTime).foregroundColor(.gray).padding(.bottom, timePadding)
	}
	
	private var totalTimeText: some View {
		playbackText(time: totalTime).padding(.bottom, timePadding)
	}
	
	private var trackThumb: some View {
		Circle()
			.position(trackPath(filled: false).currentPoint ?? trackPath(filled: true).currentPoint ?? .zero)
			.transformEffect(.identity.translatedBy(x: -size/2.0, y: -size/2.0))
//			.scaleEffect(x: 1.2, y: 1.2, anchor: UnitPoint(x: 0.5, y: 0.5))
			.frame(width: 16, height: 16)
	}
	
	private func trackPath(filled: Bool) -> Path {
		Path(ellipseIn: .init(x: 0, y: 0, width: size, height: size))
			.trimmedPath(from: filled ? 0.0 : percentComplete/2.0, to: filled ? percentComplete/2.0 : 0.5)
			.strokedPath(StrokeStyle(lineWidth: 4))
			.applying(.identity.scaledBy(x: -1, y: 1).translatedBy(x: -size, y: 0))
	}
	
	private func track(filled: Bool) -> some View {
		trackPath(filled: filled)
			.frame(width: size, height: size)
			.scaleEffect(x: 1.2, y: 1.2, anchor: UnitPoint(x: 0.5, y: 0.5))
			.foregroundStyle(trackColor(filled: filled))
	}
	
	private func trackColor(filled: Bool) -> AnyShapeStyle {
		filled
		? AnyShapeStyle(LinearGradient(colors: [NEWMColor.pink2.swiftUIColor,
												NEWMColor.purple2.swiftUIColor],
									   startPoint: .bottom,
									   endPoint: .top))
		: AnyShapeStyle(.gray)
	}
	
	private func playbackText(time: String) -> some View {
		Text(time)
			.font(.caption2)
			.monospacedDigit()
	}
	
	private var artistImage: some View {
		AsyncImage(url: URL(string: artistImageUrl)) { phase in
			switch phase {
			case .empty:
				Image.placeholder.circleImage(size: size)
			case .failure(let error):
				//TODO: log error
				Image.placeholder.circleImage(size: size)
			case .success(let image):
				image.circleImage(size: size)
			@unknown default:
				Image.placeholder.circleImage(size: size)
			}
		}
	}
}

struct PlaybackCounter_Previews: PreviewProvider {
	static let totalTime: Int = 10

	class PreviewViewModel: ObservableObject {
		@Published var currentTime: Int = 0
		private var timer: Timer? = nil
		
		init() {
			timer = Timer.scheduledTimer(withTimeInterval: 1.0, repeats: true, block: { [weak self] _ in
				guard self?.currentTime ?? 0 < PlaybackCounter_Previews.totalTime else { return }
				self?.currentTime += 1
			})
		}
	}
	
	struct PreviewView: View {
		@StateObject var viewModel = PreviewViewModel()
		
		var body: some View {
			PlaybackCounter(currentTime: viewModel.currentTime.playbackTimeString,
							totalTime: PlaybackCounter_Previews.totalTime.playbackTimeString,
							percentComplete: CGFloat(viewModel.currentTime) / CGFloat(PlaybackCounter_Previews.totalTime),
							artistImageUrl: SharedUI.MockData.artistImageUrl,
							size: 250)
		}
	}
	
	static var previews: some View {
		PreviewView()
			.preferredColorScheme(.dark)
	}
}
