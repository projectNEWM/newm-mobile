package io.newm.feature.barcode.scanner

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.stringResource
import io.newm.core.resources.R
import io.newm.core.ui.buttons.SecondaryButton
import io.newm.core.ui.text.TextFieldWithLabel
import io.newm.core.ui.text.formTitleStyle

class BarcodeScannerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Box(modifier = Modifier.fillMaxSize()) {
                PreviewViewComposable()
                CameraOverlay()
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp, horizontal = 16.dp)
                        .align(Alignment.TopStart),
                    text = stringResource(id = R.string.barcode_scanner_connect_wallet),
                    style = formTitleStyle
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                        .align(Alignment.BottomCenter),
                ) {
                    TextFieldWithLabel(
                        labelResId = R.string.barcode_scanner_paste_xpub_key,
                        onValueChange = { value ->
                            if (value.startsWith("xpub")) {
                                onValidXpubKey(value)
                            }
                        },
                    )
                    SecondaryButton(text = stringResource(id = R.string.barcode_scanner_use_test_xpub_key),
                        onClick = { onValidXpubKey(TEST_XPUB_KEY) })
                }

            }
        }
    }

    @Composable
    fun CameraOverlay(modifier: Modifier = Modifier) {
        Box(
            modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier.matchParentSize(), content = {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    clipPath(
                        path = Path().apply {
                            addRect(
                                Rect(
                                    left = (size.width - 250.dp.toPx()) / 2,
                                    top = (size.height - 250.dp.toPx()) / 2,
                                    right = (size.width + 250.dp.toPx()) / 2,
                                    bottom = (size.height + 250.dp.toPx()) / 2
                                )
                            )
                        }, clipOp = ClipOp.Difference
                    ) {
                        drawRect(Color.Black.copy(alpha = 0.8f))
                    }
                }
            })

            Box(
                modifier = Modifier
                    .size(250.dp)
                    .border(2.dp, Color.White, RectangleShape)
            )
        }
    }

    private fun onValidXpubKey(xpubKey: String) {
        val resultIntent = Intent().apply {
            putExtra("SCAN_RESULT", xpubKey)
        }
        this.apply {
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
        Toast.makeText(this, "Barcode found", Toast.LENGTH_SHORT).show()
    }

    @Composable
    fun PreviewViewComposable() {
        AndroidView(
            { context ->
                val cameraExecutor = Executors.newSingleThreadExecutor()
                val previewView = PreviewView(context).also {
                    it.scaleType = PreviewView.ScaleType.FILL_CENTER
                }
                val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
                cameraProviderFuture.addListener({
                    val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                    val imageCapture = ImageCapture.Builder().build()

                    val imageAnalyzer = ImageAnalysis.Builder().build().also {
                        it.setAnalyzer(cameraExecutor, BarcodeAnalyser { barcodeResult ->
                            // Return the result to the calling activity
                            onValidXpubKey(barcodeResult)
                        })
                    }

                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                    try {
                        // Unbind use cases before rebinding
                        cameraProvider.unbindAll()

                        // Bind use cases to camera
                        cameraProvider.bindToLifecycle(
                            context as ComponentActivity,
                            cameraSelector,
                            preview,
                            imageCapture,
                            imageAnalyzer
                        )

                    } catch (exc: Exception) {
                        Log.e("DEBUG", "Use case binding failed", exc)
                    }
                }, ContextCompat.getMainExecutor(context))
                previewView
            }, modifier = Modifier.fillMaxSize()
        )
    }

    class BarcodeAnalyser(
        val onValidScan: (String) -> Unit
    ) : ImageAnalysis.Analyzer {
        @OptIn(ExperimentalGetImage::class)
        override fun analyze(imageProxy: ImageProxy) {
            val options =
                BarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_QR_CODE).build()

            val scanner = BarcodeScanning.getClient(options)
            val mediaImage = imageProxy.image
            mediaImage?.let {
                val image =
                    InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

                scanner.process(image).addOnSuccessListener { barcodes ->
                    if (barcodes.size > 0) {
                        barcodes.forEach {
                            if (it.rawValue?.startsWith("xpub") == true) {
                                Log.d("DEBUG cje466", "Barcode found: ${it.rawValue}")
                                onValidScan(it.rawValue.toString())
                            }
                        }
                    }
                }.addOnFailureListener {
                    // Task failed with an exception
                    // ...
                }
            }
            imageProxy.close()
        }
    }

    companion object {
        private val TEST_XPUB_KEY =
            "xpub1j6l5sgu597d72mu6tnzmrlt3mfv8d8qru2ys5gy4hf09g2v97ct8gslwcvkjyd8jkpefj226ccyw6al76af5hcf328myun6pwjl7wcgshjjxl"
    }
}