package io.newm.screens.marketplace

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import io.newm.screens.profile.view.ProfileUiState
import io.newm.shared.public.analytics.NewmAppEventLogger
import io.newm.shared.public.analytics.events.AppScreens
import io.newm.shared.public.usecases.HasWalletConnectionsUseCase
import io.newm.shared.public.usecases.UserDetailsUseCase
import kotlinx.coroutines.flow.distinctUntilChanged

class MarketplacePresenter(
    private val navigator: Navigator,
    private val eventLogger: NewmAppEventLogger
) : Presenter<MarketplaceState> {
    @Composable
    override fun present(): MarketplaceState {
        val context = LocalContext.current
        val connectivityManager = remember {
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }
        val isNetworkAvailable by remember {
            mutableStateOf(connectivityManager.activeNetwork != null)
        }
        return when {
            !isNetworkAvailable -> MarketplaceState.Error
            else -> {
                eventLogger.logPageLoad(AppScreens.MarketplaceScreen.name)
                MarketplaceState.Content(
                    eventSink = {}
                )
            }
        }
    }
}
