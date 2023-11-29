import Foundation
import SwiftUI
import QRCodeReader
import AVFoundation

public struct QRCodeScannerView: UIViewControllerRepresentable {
	public class Coordinator: NSObject, QRCodeReaderViewControllerDelegate {
		public var parent: QRCodeScannerView

		public init(parent: QRCodeScannerView) {
			self.parent = parent
		}

		public func reader(_ reader: QRCodeReaderViewController, didScanResult result: QRCodeReaderResult) {
			parent.scannedCode = result.value
		}

		public func readerDidCancel(_ reader: QRCodeReaderViewController) {
			parent.scannedCode = nil
		}
	}
	
	@Binding var scannedCode: String?
	
	public init(scannedCode: Binding<String?>) {
		_scannedCode = scannedCode
	}

	public func makeCoordinator() -> Coordinator {
		return Coordinator(parent: self)
	}

	public func makeUIViewController(context: Context) -> QRCodeReaderViewController {
		let reader = QRCodeReaderViewController(builder: QRCodeReaderViewControllerBuilder {
			$0.reader = QRCodeReader(metadataObjectTypes: [AVMetadataObject.ObjectType.qr])
		})
		reader.delegate = context.coordinator
		return reader
	}

	public func updateUIViewController(_ uiViewController: QRCodeReaderViewController, context: Context) {}
}

private struct ContentView: View {
	@State private var scannedCode: String?

	var body: some View {
		VStack {
			if let scannedCode = scannedCode {
				Text("Scanned code is: \(scannedCode)")
			} else {
				QRCodeScannerView(scannedCode: $scannedCode)
			}
		}
	}
}

#Preview {
	ContentView()
}
