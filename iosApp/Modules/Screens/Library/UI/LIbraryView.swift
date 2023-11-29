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
	@State private var tracks: [NFTTrack] = []
	@State private var searchText: String = ""
	@State private var error: String?
	@State private var showLoading: Bool = true
	
	@InjectedObject private var audioPlayer: VLCAudioPlayer
	@Injected private var walletNFTTracksUseCase: any WalletNFTTracksUseCase
	@Injected private var connectWalletXPubUseCase: any ConnectWalletUseCase
	
	private var filteredNFTTracks: [NFTTrack] {
		guard searchText.isEmpty == false else {
			return tracks
		}
		return tracks.filter {
			$0.name.localizedCaseInsensitiveContains(searchText)
		}
	}
	
	public var body: some View {
		Group {
			if showLoading {
				loadingView
			} else if let error {
				errorView(error)
			} else {
				loadedView
			}
		}
		.refreshable {
			try? await refresh()
		}
		.task {
			connectWalletXPubUseCase.connect(xpub: "")
			try? await refresh()
			showLoading = false
		}
	}
	
	@ViewBuilder
	private var loadingView: some View {
		ProgressView()
			.progressViewStyle(.circular)
	}
	
	@ViewBuilder
	private func errorView(_ error: String) -> some View {
		Text(error)
	}
	
	@ViewBuilder
	private var loadedView: some View {
		NavigationView {
			List {
				ForEach(filteredNFTTracks, id: \.id) { audioTrack in
					row(for: audioTrack)
						.frame(height: 40)
						.padding(.leading, -6)
						.padding([.bottom, .top], -1)
				}
				.listRowSeparator(.hidden)
			}
		}
		.searchable(text: $searchText, prompt: "Search")
	}
	
	@ViewBuilder
	fileprivate func row(for track: NFTTrack) -> some View {
		Button(action: {
			if audioPlayer.playQueueIsEmpty {
				audioPlayer.setPlayQueue(tracks, playFirstTrack: false)
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
				
				Text(track.duration.playbackTimeString)
					.font(Font.inter(ofSize: 12))
					.foregroundStyle(try! Color(hex: "8F8F91"))
			}
			.foregroundStyle(.white)
			.tag(track.id)
		}
//		.swipeActions {
//			Button(action: {
//				
//			}, label: {
//				VStack(alignment: .center) {
//					Asset.Media.download.swiftUIImage
//					Text("Download")
//						.font(Font.interMedium(ofSize: 12))
//						.foregroundStyle(Color.white)
//				}
//				.padding()
//			})
//		}
	}
	
	private func progressView(for track: NFTTrack) -> some View {
		guard let progress = audioPlayer.loadingProgress[track] else { return EmptyView().erased }
		return Gauge(value: progress, in: 0...1) { }
			.gaugeStyle(.accessoryCircularCapacity)
			.scaleEffect(0.5)
			.erased
	}
	
	@MainActor
	private func refresh() async throws {
		tracks = try await walletNFTTracksUseCase.getAllNFTTracks()
	}
}

//#if DEBUG
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
//#endif
