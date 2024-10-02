import Foundation
import SwiftUI
import Resolver
import SharedUI
import shared
import Fonts
import Colors
import ModuleLinker
import Kingfisher
import AudioPlayer
import Mocks

struct LibraryView: View {
	@StateObject private var viewModel = LibraryViewModel()
	@State var showFilter = false
	
	public var body: some View {
		NavigationView {
			Group {
				if viewModel.showLoading {
					loadingView
				} else if viewModel.showNoSongsMessage {
					noSongsMessage
				} else {
					loadedView
				}
			}
			.padding()
			.refreshable {
				await viewModel.refresh()
			}
			.sheet(isPresented: .constant(viewModel.showCodeScanner), onDismiss: {
				viewModel.scannerDismissed()
			}) {
				ConnectWalletToAccountScannerView {
					viewModel.codeScanned()
				}
			}
			.sheet(isPresented: $showFilter) {
				filterView
					.presentationDetents([.height(350)])
			}
			.alert(isPresented: .constant(viewModel.errors.currentError != nil), error: viewModel.errors.currentError) {
				Button {
					viewModel.errors.popFirstError()
				} label: {
					Text("Ok")
				}
			}
			.navigationBarTitleDisplayMode(.inline)
			.toolbar {
				ToolbarItem(placement: .topBarLeading) {
					Text(String.library)
						.font(.newmTitle1)
						.foregroundStyle(Gradients.libraryGradient.gradient)
				}
				if viewModel.filteredSortedTracks.isEmpty == false {
					ToolbarItem(placement: .topBarTrailing) {
						Button(action: {
							showFilter = true
						}, label: {
							Image("Filter Icon")
								.resizable()
								.renderingMode(.template)
								.frame(width: 30, height: 30)
						})
					}
				}
			}
		}
	}
	
	@ViewBuilder
	fileprivate var noSongsMessage: some View {
		ZStack {
			VStack {
				Text("Your library is empty.")
					.font(
						Font.custom("Inter", size: 24)
							.weight(.bold)
					)
					.multilineTextAlignment(.center)
					.foregroundColor(.white)
					.frame(width: 358, alignment: .top)
					.padding(.bottom)
				
				Text("Time to rescue it with your epic music stash!\nLet’s fill this up. 🎶")
					.font(
						Font.custom("Inter", size: 14)
							.weight(.medium)
					)
					.multilineTextAlignment(.center)
					.foregroundColor(Color(red: 0.56, green: 0.56, blue: 0.57))
					.frame(width: 358, alignment: .top)
					.padding(.bottom)
				
				if viewModel.walletIsConnected {
					Link(destination: URL(string: "https://newm.io/recordstore")!) {
						Text("Visit Record Store")
							.frame(maxWidth: .infinity)
							.padding()
							.background(Gradients.mainPrimary.opacity(0.08))
							.foregroundColor(NEWMColor.midMusic.swiftUIColor)
							.cornerRadius(8)
					}
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
	private var loadedView: some View {
		NavigationView {
			List {
				ForEach(viewModel.filteredSortedTracks, id: \.id) { audioTrack in
					row(for: audioTrack)
						.frame(height: 40)
						.padding(.leading, -6)
						.padding([.bottom, .top], -1)
						.swipeActions(allowsFullSwipe: false) {
							Button {
								viewModel.swipeAction(for: audioTrack)
							} label: {
								Text(viewModel.swipeText(for: audioTrack))
							}
						}
				}
				.listRowSeparator(.hidden)
			}
		}
		.searchable(text: $viewModel.searchText, prompt: "Search")
	}
	
	@ViewBuilder
	fileprivate func row(for track: NFTTrack) -> some View {
		Button(action: {
			viewModel.trackTapped(track)
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
					Text(track.title)
						.font(Font.interMedium(ofSize: 14))
						.foregroundStyle(viewModel.trackIsPlaying(track) ? NEWMColor.pink() : .white)
					HStack(alignment: .center, spacing: 4) {
						if viewModel.trackIsDownloaded(track) {
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
		if let progress = viewModel.loadingProgress(for: track) {
			if 0 < progress, progress < 1 {
				Gauge(value: progress, in: 0...1) { }
					.gaugeStyle(.accessoryCircularCapacity)
					.scaleEffect(0.5)
			} else {
				ProgressView()
			}
		} else {
			EmptyView()
		}
	}
	
	@ViewBuilder
	var filterView: some View {
		ZStack {
			Color.black
			VStack(alignment: .leading, spacing: 20) {
				Text("Filter songs under")
					.foregroundColor(.white)
				
				Button(action: {
					viewModel.toggleLengthFilter()
				}) {
					HStack {
						Text("30 seconds")
							.foregroundColor(viewModel.durationFilter == 30 ? .black : NEWMColor.midMusic())
						Spacer()
						Image(systemName: "checkmark")
							.foregroundColor(.black)
					}
					.padding()
					.background(viewModel.durationFilter == 30 ? NEWMColor.midMusic().erased : Gradients.mainPrimaryLight.erased)
					.cornerRadius(8)
				}
				.frame(height: 40)
				
				Text("Sort by")
					.foregroundColor(.white)
					.padding(.top)
				
				VStack(alignment: .leading, spacing: 10) {
					Button(action: {
						viewModel.titleSortTapped()
					}) {
						Text(viewModel.titleSortButtonTitle)
							.frame(maxWidth: .infinity, alignment: .leading)
							.padding()
							.background(viewModel.titleSortSelected ? NEWMColor.midMusic().erased : Gradients.mainPrimaryLight.erased)
							.foregroundColor(viewModel.titleSortSelected ? .black : NEWMColor.midMusic.swiftUIColor)
							.cornerRadius(8)
					}
					
					Button(action: {
						viewModel.artistSortTapped()
					}) {
						Text(viewModel.artistSortButtonTitle)
							.frame(maxWidth: .infinity, alignment: .leading)
							.padding()
							.background(viewModel.artistSortSelected ? NEWMColor.midMusic().erased : Gradients.mainPrimaryLight.erased)
							.foregroundColor(viewModel.artistSortSelected ? .black : NEWMColor.midMusic.swiftUIColor)
							.cornerRadius(8)
					}
					
					Button(action: {
						viewModel.durationSortTapped()
					}) {
						Text(viewModel.durationSortButtonTitle)
							.frame(maxWidth: .infinity, alignment: .leading)
							.padding()
							.background(viewModel.durationSortSelected ? NEWMColor.midMusic().erased : Gradients.mainPrimaryLight.erased)
							.foregroundColor(viewModel.durationSortSelected ? .black : NEWMColor.midMusic.swiftUIColor)
							.cornerRadius(8)
					}
				}
			}
		}
		.padding()
		.background(Color.black)
	}
	
	@ViewBuilder
	func filterRow(title: String, rightView: () -> any View = { EmptyView() }) -> some View {
		HStack {
			Text(title)
			Spacer()
			rightView().erased
		}
		.foregroundStyle(NEWMColor.midMusic.swiftUIColor)
	}
	
	@ViewBuilder
	private func rightView(for sort: Sort?) -> some View {
		switch sort {
		case
				.artist(let ascending) where ascending == true,
				.duration(let ascending) where ascending == true,
				.title(let ascending) where ascending == true:
			Image(systemName: "chevron.up")
		case
				.artist(let ascending) where ascending == false,
				.duration(let ascending) where ascending == false,
				.title(let ascending) where ascending == false:
			Image(systemName: "chevron.down")
		default:
			EmptyView()
		}
	}
}

#if DEBUG
//#Preview {
//	Resolver.root = .mock
//	//	LibraryModule.shared.registerAllMockedServices(mockResolver: .root)
//	MocksModule.shared.registerAllMockedServices(mockResolver: .mock)
//	AudioPlayerModule.shared.registerAllServices()
//	let useCase = $0.resolve(ConnectWalletUseCase.self)
////	Resolver.root.register {
////		Task {
////			try await useCase.connect(walletConnectionId: "newm34r343g3g343833")
////		}
////		return useCase as ConnectWalletUseCase
////	}
//	return Group {
//		LibraryView()
//		LibraryView()
//			.row(for: NFTTrack.mocks.first!)
//	}
//	.preferredColorScheme(.dark)
//	.tint(.white)
//}

#Preview {
	Resolver.root = .mock
	LibraryModule.shared.registerAllMockedServices(mockResolver: .mock)
	return LibraryView(showFilter: false).noSongsMessage
		.preferredColorScheme(.dark)
		.tint(.white)
		.background(.black)
}
#endif
//
//struct FilterView: View {
//	@State var lengthFilterSelected = false
//	@State var sortOption = "Title (A to Z)"
//	@Environment(\.dismiss) var dismiss
//
//	static let titleAscen
//
//	var body: some View {
//	}
//}
//
//#Preview {
//	FilterView()
//}
