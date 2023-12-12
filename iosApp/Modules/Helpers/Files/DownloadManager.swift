import Foundation
import Combine
import ModuleLinker
import Resolver

class DownloadManager: NSObject, ObservableObject {
	@Published private(set) var downloads: [URL: URLSessionDownloadTask] = [:]
	private var progressHandlers: [URL: (Double) -> Void] = [:]
	
	private var urlSession: URLSession!
	private var downloadCompletionHandlers: [URL: (Result<URL, Error>) -> Void] = [:]
	
	@Injected private var logger: any ErrorReporting
	
	override init() {
		super.init()
		urlSession = URLSession(configuration: .default, delegate: self, delegateQueue: OperationQueue.main)
	}
	
	func download(url: URL, completion: @escaping (Result<URL, Error>) -> Void, progressHandler: @escaping (Double) -> Void) {
		guard downloads[url] == nil else { return }
		
		let task = urlSession.downloadTask(with: url)
		downloads[url] = task
		downloadCompletionHandlers[url] = completion
		progressHandlers[url] = progressHandler
		task.resume()
	}
	
	func download(url: URL, progressHandler: @escaping (Double) -> Void) async throws -> URL {
		try await withCheckedThrowingContinuation { [weak self] continuation in
			guard let self else { fatalError() }
			download(url: url, completion: { result in
				continuation.resume(with: result)
			}, progressHandler: progressHandler)
		}
	}
	
	private func handleDownloadCompletion(remoteUrl: URL, tmpLocalUrl: URL?, error: Error?) {
		defer {
			downloadCompletionHandlers.removeValue(forKey: remoteUrl)
			downloads.removeValue(forKey: remoteUrl)
		}
		
		if let error = error {
			logger.logError(error)
			downloadCompletionHandlers[remoteUrl]?(.failure(error))
			return
		}
		
		guard let tmpLocalUrl else {
			let error = URLError(.cannotCreateFile)
			logger.logError(error)
			downloadCompletionHandlers[remoteUrl]?(.failure(error))
			return
		}
		
		let persistentUrl = fileURL(forDownloadURL: remoteUrl)
		do {
			try FileManager.default.moveItem(at: tmpLocalUrl, to: persistentUrl)
			downloadCompletionHandlers[remoteUrl]?(.success(persistentUrl))
		} catch {
			logger.logError(error)
			downloadCompletionHandlers[remoteUrl]?(.failure(error))
		}
	}
}

extension DownloadManager: URLSessionDownloadDelegate {
	func urlSession(_ session: URLSession, downloadTask: URLSessionDownloadTask, didFinishDownloadingTo location: URL) {
		guard let url = downloadTask.originalRequest?.url else { return } //NFTTrack.songUrl
		handleDownloadCompletion(remoteUrl: url, tmpLocalUrl: location, error: nil)
	}
	
	func urlSession(_ session: URLSession, downloadTask: URLSessionDownloadTask, didWriteData bytesWritten: Int64, totalBytesWritten: Int64, totalBytesExpectedToWrite: Int64) {
		guard let url = downloadTask.originalRequest?.url else { return }
		let progress = Double(totalBytesWritten) / Double(totalBytesExpectedToWrite)
		progressHandlers[url]?(progress)
	}
}
