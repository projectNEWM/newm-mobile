@file:kotlin.OptIn(ExperimentalMaterialApi::class)

package io.newm.feature.barcode.scanner

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import io.newm.core.resources.R
import io.newm.core.theme.Black
import io.newm.core.theme.CerisePink
import io.newm.core.theme.GlassSmith
import io.newm.core.theme.Gray23
import io.newm.core.theme.Gray400
import io.newm.core.theme.Gray6F
import io.newm.core.theme.LightSkyBlue
import io.newm.core.theme.NewmTheme
import io.newm.core.theme.OceanGreen
import io.newm.core.theme.SteelPink
import io.newm.core.theme.White
import io.newm.core.theme.inter
import io.newm.core.ui.text.TextFieldWithLabel
import io.newm.core.ui.utils.textGradient
import io.newm.core.ui.wallet.buttonGradient
import io.newm.shared.NewmAppLogger
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.concurrent.Executors

val qrLabelStyle = TextStyle(
    fontSize = 12.sp,
    fontFamily = inter,
    fontWeight = FontWeight.Bold,
    color = Gray6F
)

val placeholderStyle = TextStyle(
    fontSize = 16.sp,
    fontFamily = inter,
    fontWeight = FontWeight.Normal,
    color = Gray6F
)

class BarcodeScannerActivity : ComponentActivity() {

    private val logger: NewmAppLogger by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewmTheme(darkTheme = true) {
                BarcodeScannerUi()
            }
        }
    }

    private fun onValidCodeConnection(newmCode: String) {
        val resultIntent = Intent().apply {
            putExtra(NEWM_WALLET_CONNECTION_ID, newmCode)
        }
        this.apply {
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    @Composable
    private fun HelpButton(onClick: () -> Unit) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .height(40.dp)
                .fillMaxWidth()
                .background(Black)
                .clickable { onClick() },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(id = R.string.barcode_help_text),
                style = TextStyle(
                    fontFamily = inter,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    brush = textGradient(SteelPink, CerisePink)
                )
            )
        }
    }

    @Composable
    private fun BarcodeScannerUi() {
        val bottomSheetState =
            rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden,
                skipHalfExpanded = true
            )
        val coroutineScope = rememberCoroutineScope()

        ModalBottomSheetLayout(
            sheetState = bottomSheetState,
            sheetContent = {
                InstructionList()
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .background(Black),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TopPanel()
                    Spacer(modifier = Modifier.height(40.dp))
                    PreviewViewComposable()
                    EnterQRCodePanel()
                    Spacer(modifier = Modifier.weight(1f))
                    CopyToClipboardButton()
                    HelpButton {
                        coroutineScope.launch {
                            bottomSheetState.show()
                        }
                    }
                }
            }
        )
    }

    @Composable
    fun TopPanel() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = { finish() },
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = White
                )
            }
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                text = stringResource(id = R.string.title_connect_wallet),
                style = TextStyle(
                    fontFamily = inter,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    brush = textGradient(OceanGreen, LightSkyBlue)
                )
            )
        }
    }

    @Composable
    fun CopyToClipboardButton() {
        val context = LocalContext.current
        val clipboardManager: ClipboardManager = LocalClipboardManager.current

        Button(
            onClick = {
                clipboardManager.setText(androidx.compose.ui.text.AnnotatedString("https://newm.tools/"))
                Toast
                    .makeText(context, "URL copied to clipboard", Toast.LENGTH_SHORT)
                    .show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(buttonGradient)
                .clip(RoundedCornerShape(8.dp)),
            elevation = null,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
        )
        {
            Text(
                text = "Visit https://newm.tools/",
                fontFamily = inter,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = GlassSmith
            )
            Icon(
                modifier = Modifier.padding(start = 8.dp),
                painter = painterResource(id = R.drawable.icon_copy_text),
                contentDescription = "Copy",
                tint = GlassSmith
            )
        }
    }

    @Composable
    fun EnterQRCodePanel() {
        TextFieldWithLabel(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 38.5.dp),
            labelResId = R.string.barcode_scanner_paste_qr_code,
            labelStyle = qrLabelStyle,
            placeholderResId = R.string.barcode_placeholder_text,
            placeholderStyle = placeholderStyle,
            textfieldBackgroundColor = Gray23,
            onValueChange = { newmWalletConnectionId ->
                onValidCodeConnection(newmWalletConnectionId)
            },
        )
    }

    @Composable
    fun InstructionList() {
        val instructions = listOf(
            R.string.newm_connect_wallet_instruction_1,
            R.string.newm_connect_wallet_instruction_2,
            R.string.newm_connect_wallet_instruction_3,
            R.string.newm_connect_wallet_instruction_4,
            R.string.newm_connect_wallet_instruction_5,
            R.string.newm_connect_wallet_instruction_6,
            R.string.newm_connect_wallet_instruction_7,
            R.string.newm_connect_wallet_instruction_8,
            R.string.newm_connect_wallet_instruction_9,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.surface, shape = MaterialTheme.shapes.large)
                .padding(vertical = 32.dp, horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.newm_connect_wallet_instruction_title),
                style = TextStyle(
                    fontFamily = inter,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = White
                )
            )

            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = Gray400)
            Spacer(modifier = Modifier.height(8.dp))

            instructions.forEach { instructionResId ->
                Text(
                    text = stringResource(id = instructionResId),
                    style = TextStyle(
                        fontFamily = inter,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = White
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            CopyToClipboardButton()
        }
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
                        it.surfaceProvider = previewView.surfaceProvider
                    }

                    val imageCapture = ImageCapture.Builder().build()

                    val imageAnalyzer = ImageAnalysis.Builder().build().also {
                        it.setAnalyzer(cameraExecutor, BarcodeAnalyser { barcodeResult ->
                            // Return the result to the calling activity
                            onValidCodeConnection(barcodeResult)
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
                        logger.error("BarcodeScannerActivity", "Use case binding failed", exc)
                    }
                }, ContextCompat.getMainExecutor(context))
                previewView
            },
            modifier = Modifier
                .size(278.dp)
                .clip(RoundedCornerShape(32.dp)),
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
                            if (it.rawValue?.startsWith("newm-") == true) {
                                onValidScan(it.rawValue.toString())
                            }
                        }
                    }
                }
            }
            imageProxy.close()
        }
    }

    companion object {
        const val NEWM_WALLET_CONNECTION_ID = "NEWM_WALLET_CONNECTION_ID"
    }
}