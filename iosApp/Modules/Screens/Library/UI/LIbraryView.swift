import Foundation
import SwiftUI
import Resolver
import SharedUI
import shared
import Fonts
import Colors
import AudioPlayer
import ModuleLinker
import AVFoundation
import Kingfisher

struct LibraryView: View {
	@StateObject private var viewModel = LibraryViewModel()
	@InjectedObject private var audioPlayer: VLCAudioPlayer
	
	public var body: some View {
		Group {
			if viewModel.showLoading {
				loadingView
			} else if viewModel.tracks.isEmpty {
				noSongsMessage
			} else if let error = viewModel.error {
				errorView(error)
			} else {
				loadedView
			}
		}
		.refreshable {
			await viewModel.refresh()
		}
		.task {
			await viewModel.refresh()
		}
		.sheet(isPresented: .constant(viewModel.showXPubScanner)) {
			XPubScannerView {
				viewModel.xPubScanned()
			}
		}
	}
	
	@ViewBuilder
	private var noSongsMessage: some View {
		ZStack {
			ScrollView {}
				.background {
					VStack {
						Text("Your library is empty.")
							.font(
								Font.custom("Inter", size: 24)
									.weight(.bold)
							)
							.multilineTextAlignment(.center)
							.foregroundColor(.white)
							.frame(width: 358, alignment: .top)
						
						Text("Time to rescue it with your epic music stash! 🎶")
							.font(
								Font.custom("Inter", size: 14)
									.weight(.medium)
							)
							.multilineTextAlignment(.center)
							.foregroundColor(Color(red: 0.56, green: 0.56, blue: 0.57))
							.frame(width: 358, alignment: .top)
					}
				}
			if viewModel.walletIsConnected == false {
				VStack {
					Spacer()
					ConnectWalletAlertView {
						viewModel.connectWallet()
					}
				}
			}
		}
	}
	
	@ViewBuilder
	private var loadingView: some View {
		ProgressView()
			.progressViewStyle(.circular)
	}
	
	@ViewBuilder
	private func errorView(_ error: String) -> some View {
		ScrollView {
			VStack {
				Spacer()
				Text(error)
				Spacer()
			}
		}
	}
	
	@ViewBuilder
	private var loadedView: some View {
		NavigationView {
			List {
				ForEach(viewModel.filteredNFTTracks, id: \.id) { audioTrack in
					row(for: audioTrack)
						.frame(height: 40)
						.padding(.leading, -6)
						.padding([.bottom, .top], -1)
				}
				.listRowSeparator(.hidden)
			}
		}
		.searchable(text: $viewModel.searchText, prompt: "Search")
	}
	
	@ViewBuilder
	fileprivate func row(for track: NFTTrack) -> some View {
		Button(action: {
			if audioPlayer.playQueueIsEmpty {
				audioPlayer.setPlayQueue(viewModel.tracks, playFirstTrack: false)
			}
			audioPlayer.seek(toTrack: track)
		}) {
			HStack {
				KFImage(URL(string: track.imageUrl))
					.placeholder {
						Image.placeholder
							.resizable()
							.frame(width: 40, height: 40)
							.clipShape(RoundedRectangle(cornerRadius: 4))
					}
					.setProcessor(DownsamplingImageProcessor(size: CGSize(width: 40, height: 40)))
					.clipShape(RoundedRectangle(cornerRadius: 4))
				
				VStack(alignment: .leading, spacing: 3) {
					Text(track.name)
						.font(Font.interMedium(ofSize: 14))
						.foregroundStyle(audioPlayer.trackIsPlaying(track) ? NEWMColor.pink() : .white)
					HStack(alignment: .center, spacing: 4) {
						if audioPlayer.trackIsDownloaded(track) {
							Asset.Media.checkboxCircleFill.swiftUIImage
						}
						Text(track.artists.first ?? "")
							.font(Font.inter(ofSize: 12))
							.foregroundStyle(try! Color(hex: "8F8F91"))
					}
				}
				.padding(.leading, 4)
				
				Spacer()
				
				progressView(for: track)
			}
			.foregroundStyle(.white)
			.tag(track.id)
		}
	}
	
	@ViewBuilder
	private func progressView(for track: NFTTrack) -> some View {
		guard let progress = audioPlayer.loadingProgress[track] else { return EmptyView().erased }
		return Gauge(value: progress, in: 0...1) { }
			.gaugeStyle(.accessoryCircularCapacity)
			.scaleEffect(0.5)
			.erased
	}
}

#if DEBUG
struct LibraryView_Previews: PreviewProvider {
	static var previews: some View {
		Resolver.root = Resolver.mock
		AudioPlayerModule.shared.registerAllServices()
		return Group {
			LibraryView()
			LibraryView()
				.row(for: NFTTrack.mockTracks.first!)
				.padding()
				.previewDisplayName("Row")
		}
		.preferredColorScheme(.dark)
		.tint(.white)
	}
}
#endif
