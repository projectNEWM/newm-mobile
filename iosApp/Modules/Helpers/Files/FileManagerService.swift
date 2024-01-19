import Foundation
import Combine
import ModuleLinker
import shared
import Resolver

public class FileManagerService: ObservableObject {
	public typealias ProgressHandler = (Double) -> Void

	let downloadManager = DownloadManager()
	
	@Injected private var errorLogger: ErrorReporting
	private var cancels: Set<AnyCancellable> = []
	
	public init() {
		downloadManager.objectWillChange.sink { [weak self] in self?.objectWillChange.send() }.store(in: &cancels)
	}
	
	/// Returns the downloaded URL if available, otherwise the remote URL.
	public func getPlaybackURL(for downloadURL: URL) -> URL {
		return if fileExists(for: downloadURL) {
			fileURL(forDownloadURL: downloadURL)
		} else {
			downloadURL
		}
	}
	
	public func fileExists(for url: URL) -> Bool {
		FileManager.default.fileExists(atPath: fileURL(forDownloadURL: url).path)
	}
	
	public func clearFiles() {
		let fileManager = FileManager.default
		guard let documentsDirectory = fileManager.urls(for: .documentDirectory, in: .userDomainMask).first else { return }

		do {
			let fileURLs = try fileManager.contentsOfDirectory(at: documentsDirectory, includingPropertiesForKeys: nil)

			for fileURL in fileURLs {
				try fileManager.removeItem(at: fileURL)
			}
		} catch {
			errorLogger.logError("Error clearing documents directory: \(error)")
		}
	}
	
	public func clearFile(at url: URL) {
		do {
			try FileManager.default.removeItem(at: fileURL(forDownloadURL: url))
			objectWillChange.send()
		} catch {
			errorLogger.logError("Error clearing documents directory: \(error)")
		}
	}
	
	public func download(url: URL, progressHandler: @escaping ProgressHandler) async throws {
		progressHandler(0)
		try await downloadManager.download(url: url, progressHandler: progressHandler)
	}
	
	public func cancelDownload(url: URL) {
		downloadManager.cancelDownload(url: url)
	}
}

func fileURL(forDownloadURL downloadURL: URL) -> URL {
	FileManager.default.documentsDirectory.appendingPathComponent(downloadURL.lastPathComponent)
}

extension FileManager {
	var documentsDirectory: URL {
		return FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)[0]
	}
}
