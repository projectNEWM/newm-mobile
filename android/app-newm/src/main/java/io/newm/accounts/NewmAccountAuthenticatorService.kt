package io.newm.accounts

import android.app.Service
import android.content.Intent
import android.os.IBinder


internal class NewmAccountAuthenticatorService : Service() {
    override fun onBind(intent: Intent): IBinder? = NewmAccountAuthenticator(this).iBinder
}
