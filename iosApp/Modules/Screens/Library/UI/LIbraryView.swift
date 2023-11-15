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

struct LibraryView: View {
	@State private var tracks: [NFTTrack] = []
	@State private var searchText: String = ""
	@Injected private var audioPlayer: AudioPlayer
	
	private var filteredNFTTracks: [NFTTrack] {
		guard searchText.isEmpty == false else {
			return tracks
		}
		return tracks.filter {
			$0.name.localizedCaseInsensitiveContains(searchText)
		}
	}
	
	public var body: some View {
		NavigationView {
			List {
				ForEach(filteredNFTTracks, id: \.name) { audioTrack in
					row(for: audioTrack)
				}
				.listRowSeparator(.hidden)
			}
		}
		.searchable(text: $searchText, prompt: "Search")
		.task {
			try? await refresh()
		}
	}
	
	@ViewBuilder
	private func row(for track: NFTTrack) -> some View {
		HStack {
			AsyncImage(url: URL(string: track.imageUrl)) { image in
				image
					.resizable()
					.frame(width: 40, height: 40)
			} placeholder: {
				Image.placeholder
					.frame(width: 40, height: 40)
			}
			.clipShape(RoundedRectangle(cornerRadius: 4))
			VStack(alignment: .leading) {
				Text(track.name)
					.font(Font.interMedium(ofSize: 14))
				Text(track.artists.first ?? "")
					.font(Font.inter(ofSize: 12))
					.foregroundStyle(try! Color(hex: "8F8F91"))
			}
			Spacer()
			Text("4:20")
		}
		.onTapGesture {
			if let item = AudioItem(highQualitySoundURL: URL(string: track.songUrl)) {
				audioPlayer.play(item: item)
			} else {
				//TODO: handle error
			}
		}
		.swipeActions {
			Button(action: {
				
			}, label: {
				VStack(alignment: .center) {
					Asset.Media.download.swiftUIImage
					Text("Download")
						.font(Font.interMedium(ofSize: 12))
						.foregroundStyle(Color.white)
				}
				.padding()
			})
		}
	}
	
	@MainActor
	private func refresh() async throws {
		ConnectWalletUseCaseProvider().get().connect(xpub: "xpub1j6l5sgu597d72mu6tnzmrlt3mfv8d8qru2ys5gy4hf09g2v97ct8gslwcvkjyd8jkpefj226ccyw6al76af5hcf328myun6pwjl7wcgshjjxl")
		tracks = try await WalletNFTSongsUseCaseProvider().get().getWalletNFTs()
		tracks = NFTTrack.mockTracks
	}
}

struct LibraryView_Previews: PreviewProvider {
	static var previews: some View {
		AudioPlayerModule.shared.registerAllServices()
		return LibraryView()
			.preferredColorScheme(.dark)
	}
}

