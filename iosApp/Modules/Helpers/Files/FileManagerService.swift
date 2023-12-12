import Foundation
import Combine
import ModuleLinker
import shared

public class FileManagerService: ObservableObject {
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
		return FileManager.default.fileExists(atPath: fileURL(forDownloadURL: url).path)
	}
}

func fileURL(forDownloadURL downloadURL: URL) -> URL {
	getDocumentsDirectory().appendingPathComponent(downloadURL.lastPathComponent)
}

func getDocumentsDirectory() -> URL {
	return FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)[0]
}
