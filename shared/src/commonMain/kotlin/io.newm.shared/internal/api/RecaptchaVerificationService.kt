//package io.newm.shared.internal.api
//
//import com.google.android.gms.recaptcha.Recaptcha
//import com.google.android.gms.recaptcha.RecaptchaClient
//import com.google.android.recaptcha.RecaptchaClient
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.launch
//
//
//class RecaptchaVerificationService: HumanVerificationService {
//    private val applicationScope = CoroutineScope(Job() + Dispatchers.Main)
//    private var recaptchaClient: RecaptchaClient? = null
//
//    override suspend fun setUp() {
//        applicationScope.launch {
//                val client = Recaptcha.getClient(get(), keyId)
//                recaptchaClient = client
//        }
//    }
//
//    override suspend fun verify(action: HumanVerificationAction) {
//        applicationScope.launch {
//            try {
//                val client = Recaptcha.getClient(application, keyId)
//                recaptchaClient = client
//            } catch (exception: Exception) {
//                // Handle communication errors ...
//            }
//        }
//    }
//}