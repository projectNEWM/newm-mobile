import Foundation
import Kingfisher
import ModuleLinker
import Resolver

public class KingfisherErrorHandler: ImageDownloaderDelegate {
	public init() {}
	
	public func imageDownloader(_ downloader: ImageDownloader, didFinishDownloadingImageForURL url: URL, with response: URLResponse?, error: Error?) {
		error.flatMap(Resolver.resolve(ErrorReporting.self).logError)
	}
}
