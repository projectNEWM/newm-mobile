import Foundation
import Combine
import ModuleLinker
import shared

public class FileManagerService: ObservableObject {
	public typealias CompletionHandler = (Result<URL, Error>) -> Void
	public typealias ProgressHandler = (Double) -> Void

	private let downloadManager = DownloadManager()
	
	public init() {}
	
	public func getFile(for downloadURL: URL, progress: @escaping ProgressHandler) async throws -> URL {
		if fileExists(for: downloadURL) {
			return fileURL(forDownloadURL: downloadURL)
		} else {
			progress(0)
			return try await downloadManager.download(url: downloadURL, progressHandler: progress)
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
			print("Error clearing documents directory: \(error)")
		}
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
