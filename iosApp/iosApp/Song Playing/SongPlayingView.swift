import SwiftUI

struct SongPlayingView: DataView {
	@ObservedObject var viewModel: SongPlayingViewModel
	
	init(id: String) {
		viewModel = SongPlayingViewModel(songInfoUseCase: DummyData.songInfoUseCase,
										 musicPlayerUseCase: DummyData.musicPlayerUseCase)
	}
	
	var body: some View {
		VStack {
			songTitle
			artistName
			topButtons
			albumImage
			playControls
			playbackTime
			lyrics
			tipping
			supportArtistPrompt
			Spacer()
		}
		.background(backgroundImage)
		.overlay {
			if viewModel.showTipping {
				tippingOverlay
			}
		}
	}
	
	private var supportArtistPrompt: some View {
		Text(viewModel.supportArtistPrompt)
			.font(.caption3)
	}
	
	private var tipping: some View {
		Image("Tipping Icon")
			.padding(.bottom, 5)
			.onTapGesture {
				withAnimation {
					viewModel.supportArtistTapped()
				}
			}
	}
	
	private var topButtons: some View {
		HStack {
			shareButton
			Spacer()
			airplayButton
			Spacer()
			starButton
		}
		.frame(width: 250)
		.tint(.white)
	}
	
	private var starButton: some View {
		Button(action: viewModel.starTapped) {
			Image("Stars Icon")
			Text(viewModel.song.starCount)
				.padding(.leading, -5)
		}
	}
	
	private var airplayButton: some View {
		Button(action: viewModel.airplayTapped) {
			Image("Airplay Icon")
		}
	}
	
	private var shareButton: some View {
		Button(action: viewModel.shareTapped) {
			Text(viewModel.song.shareCount)
				.padding(.trailing, -5)
			Image("Share Icon")
		}
	}
	
	private var songTitle: some View {
		Text(viewModel.song.songTitle)
			.fontWeight(.heavy)
			.font(.largeTitle)
			.padding(.bottom, 10)
	}
	
	private var artistName: some View {
		Text(viewModel.song.artistName)
			.font(.system(size: 16))
			.foregroundColor(.orange)
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
				GeometryReader { geometry in
					HStack {
						Spacer()
						image
							.circleImage(size: geometry.size.height)
						Spacer()
					}
				}
				.frame(maxHeight: 200, alignment: .center)
			default: Color.clear
			}
		}
		.padding(.top)
	}
	
	private var playControls: some View {
		HStack {
			prevButton
			repeatButton
			switch viewModel.playbackState {
			case .playing:
				pauseButton
			case .paused:
				playButton
			}
			shuffleButton
			nextButton
		}
		.frame(height: 100)
		.padding(.top, -20)
	}
	
	private var nextButton: some View {
		Button(action: viewModel.nextSongTapped) {
			Image("Next Icon")
				.padding(.bottom, 100)
				.padding(.trailing, 10)
		}
	}
	
	private var shuffleButton: some View {
		Button(action: viewModel.shuffleTapped) {
			Image("Shuffle Icon")
				.padding(.bottom, 25)
				.padding(.trailing, 10)
		}
	}
	
	private var playButton: some View {
		Button(action: viewModel.playTapped) {
			Image("Play Icon")
				.padding(.top, 20)
				.padding(.trailing, 10)
		}
	}
	
	private var pauseButton: some View {
		Button(action: viewModel.pauseTapped) {
			Image("Pause Icon")
				.padding(.top, 20)
				.padding(.trailing, 10)
		}
	}
	
	private var prevButton: some View {
		Button(action: viewModel.previousSongTapped) {
			Image("Previous Icon")
				.padding(.bottom, 100)
				.padding(.trailing, 10)
		}
	}
	
	private var repeatButton: some View {
		Button(action: viewModel.repeatTapped) {
			Image("Repeat all Icon")
				.padding(.bottom, 25)
				.padding(.trailing, 10)
		}
	}
	
	private var playbackTime: some View {
		Text(viewModel.playbackTime)
			.foregroundColor(.orange)
			.font(.title2)
			.monospacedDigit()
	}
	
	private var lyrics: some View {
		ScrollView(.vertical, showsIndicators: false) {
			Text(viewModel.song.lyrics)
		}
		.padding()
		.frame(minHeight: 100)
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
