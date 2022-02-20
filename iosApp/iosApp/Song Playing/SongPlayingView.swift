import SwiftUI
import shared

struct SongPlayingView: DataView {
	@ObservedObject var viewModel: SongPlayingViewModel
	
	init(id: String) {
		viewModel = SongPlayingViewModel(songInfoUseCase: DummyData.songInfoUseCase,
										 musicPlayerUseCase: DummyData.musicPlayerUseCase)
	}
	
	var body: some View {
		VStack {
			Text(viewModel.song.songTitle)
				.fontWeight(.heavy)
				.font(.largeTitle)
				.padding(.bottom, 10)
			Text(viewModel.song.artistName)
				.font(.system(size: 16))
				.foregroundColor(.orange)
			HStack {
				Text(viewModel.song.shareCount)
				Image("Share Icon")
					.onTapGesture(perform: viewModel.shareTapped)
				Spacer()
				Image("Airplay Icon")
					.onTapGesture(perform: viewModel.airplayTapped)
				Spacer()
				Image("Stars Icon")
					.onTapGesture(perform: viewModel.starTapped)
				Text(viewModel.song.starCount)
			}
			.frame(width: 250)
			albumImage
				.padding(.top)
			playControls
				.padding(.top, -20)
			playbackTime
			lyrics
				.padding()
			Image("Tipping Icon")
				.padding(.bottom, 5)
				.onTapGesture {
					withAnimation {
						viewModel.supportArtistTapped()
					}
				}
			Text(viewModel.supportArtistPrompt)
				.font(.caption3)
			Spacer()
		}
		.background(backgroundImage)
		.overlay {
			if viewModel.showTipping {
				tippingOverlay
			}
		}
	}
	
	private var backgroundImage: some View {
		AsyncImage(url: viewModel.song.backgroundImage) { phase in
			switch phase {
			case .success(let image):
				image
					.resizable()
					.scaledToFill()
					.ignoresSafeArea()
					.opacity(0.2)
			default: EmptyView()
			}
		}
	}
	
	private var albumImage: some View {
		AsyncImage(url: viewModel.song.albumImage) { phase in
			switch phase {
			case .success(let image):
				image
					.circleImage(size: 200)
			default: Color.clear
			}
		}
	}
	
	private var playControls: some View {
		HStack {
			Image("Previous Icon")
				.padding(.bottom, 100)
				.padding(.trailing, 10)
				.onTapGesture(perform: viewModel.previousSongTapped)
			Image("Repeat all Icon")
				.padding(.bottom, 25)
				.padding(.trailing, 10)
				.onTapGesture(perform: viewModel.repeatTapped)
			switch viewModel.playbackState {
			case .playing:
				Image("Pause Icon")
					.padding(.top, 20)
					.padding(.trailing, 10)
					.onTapGesture(perform: viewModel.playTapped)
			case .paused:
				Image("Play Icon")
					.padding(.top, 20)
					.padding(.trailing, 10)
					.onTapGesture(perform: viewModel.pauseTapped)
			}
			Image("Shuffle Icon")
				.padding(.bottom, 25)
				.padding(.trailing, 10)
			Image("Next Icon")
				.padding(.bottom, 100)
				.padding(.trailing, 10)
		}
		.frame(height: 100)
	}
	
	private var playbackTime: some View {
		Text(viewModel.playbackTime)
			.foregroundColor(.orange)
			.font(.title2)
	}
	
	private var lyrics: some View {
		ScrollView(.vertical, showsIndicators: false) {
			Text(viewModel.song.lyrics)
		}
	}
	
	private var tippingOverlay: some View {
		TipView { tipAmount in
			withAnimation {
				viewModel.tipTapped(tipAmount)
			}
		}
	}
}

struct SongPlayingView_Previews: PreviewProvider {
	static var previews: some View {
		SongPlayingView(id: "1")
			.preferredColorScheme(.dark)
	}
}
