//import Foundation
//import Combine
//import ModuleLinker
//import Resolver
//
//protocol Downloader {
//	func download(url: URL, progressHandler: @escaping (Double) -> Void) async throws -> URL
//}
//
//class DownloadManager: NSObject, ObservableObject {
//	@Published private var downloads: Set<URL> = []
////	private var progressHandlers: [URL: (Double) -> Void] = [:]
//	
//	private var urlSession: URLSession!
//	private var downloadCompletionHandlers: [URL: (Result<URL, Error>) -> Void] = [:]
//	@Injected private var logger: any ErrorReporting
//	private let downloader: Downloader
//	
////	override init() {
////		super.init()
////		urlSession = URLSession(configuration: .default, delegate: self, delegateQueue: OperationQueue.main)
////	}
//	
//	override init(downloader: Downloader) {
//		self.downloader = downloader
//	}
//	
//	func download(url: URL, progressHandler: @escaping (Double) -> Void) async throws -> URL {
//		guard downloads.ocontains(url) == false else { return }
//		
//		downloads.append(url)
//		downloadCompletionHandlers[url] = completion
//		
////		urlSession.downloadTask(with: url).resume()
//		let localUrl = await downloader.download(url: url, progressHandler: progressHandler)
//		handleDownloadCompletion(remoteUrl: <#T##URL#>, tmpLocalUrl: <#T##URL?#>, error: <#T##Error?#>)
//	}
//	
//	private func handleDownloadCompletion(remoteUrl: URL, tmpLocalUrl: URL?, error: Error?) {
//		defer {
//			downloadCompletionHandlers.removeValue(forKey: remoteUrl)
//			downloads.remove(remoteUrl)
//		}
//		
//		if let error = error {
//			logger.logError(error)
//			downloadCompletionHandlers[remoteUrl]?(.failure(error))
//			return
//		}
//		
//		guard let tmpLocalUrl else {
//			let error = URLError(.cannotCreateFile)
//			logger.logError(error)
//			downloadCompletionHandlers[remoteUrl]?(.failure(error))
//			return
//		}
//		
//		let persistentUrl = fileURL(forDownloadURL: remoteUrl)
//		do {
//			try FileManager.default.moveItem(at: tmpLocalUrl, to: persistentUrl)
//			downloadCompletionHandlers[remoteUrl]?(.success(persistentUrl))
//		} catch {
//			logger.logError(error)
//			downloadCompletionHandlers[remoteUrl]?(.failure(error))
//		}
//	}
//}
//
//extension DownloadManager: URLSessionDownloadDelegate {
//	func urlSession(_ session: URLSession, downloadTask: URLSessionDownloadTask, didFinishDownloadingTo location: URL) {
//		guard let url = downloadTask.originalRequest?.url else { return } //NFTTrack.songUrl
//		handleDownloadCompletion(remoteUrl: url, tmpLocalUrl: location, error: nil)
//	}
//	
//	func urlSession(_ session: URLSession, downloadTask: URLSessionDownloadTask, didWriteData bytesWritten: Int64, totalBytesWritten: Int64, totalBytesExpectedToWrite: Int64) {
//		guard let url = downloadTask.originalRequest?.url else { return }
//		let progress = Double(totalBytesWritten) / Double(totalBytesExpectedToWrite)
//		progressHandlers[url]?(progress)
//	}
//}
